package co.ajeg.tutoflash.firebase.database.manager;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import co.ajeg.tutoflash.model.materia.MateriaTutor;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

public class DatabaseMateria {

    private static FragmentActivity activity;
    private static DatabaseMateria thisClass;
    private ListenerRegistration listenerSolicitudes;
    private ListenerRegistration listenerMaterias;
    private DatabaseNotificacion databaseNotificacion;

    private DatabaseMateria(FragmentActivity activity) {
        this.activity = activity;
        this.databaseNotificacion = DatabaseNotificacion.getInstance(activity);
    }

    static public DatabaseMateria getInstance(FragmentActivity activity) {
        activity = activity;
        if (thisClass == null) {
            thisClass = new DatabaseMateria(activity);
        }
        return thisClass;
    }

    public void findMateriasForName(String name, OnCompleteListenerAllMaterias onCompleteListenerAllMaterias) {
        String temaString = name.trim().replaceAll(" ", "").toLowerCase();
        activity.runOnUiThread(() -> {
            getRefCollectionAllMaterias().whereEqualTo("name", temaString).get().addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    List<Materia> materias = task.getResult().toObjects(Materia.class);
                    onCompleteListenerAllMaterias.onLoadAllMaterias(materias);
                } else {
                    onCompleteListenerAllMaterias.onLoadAllMaterias(null);
                }
            });
        });
    }

    public void createTema(String materiaName, MateriaTema materiaTema, OnCompleteListenerTema onCompleteListenerTema) {
        activity.runOnUiThread(() -> {
            String temaString = materiaName.trim().replaceAll(" ", "").toLowerCase();
            getRefCollectionAllMaterias().document(temaString).get().addOnCompleteListener(result -> {
                if (result.isSuccessful()) {
                    DocumentSnapshot documentReference = result.getResult();
                    if (documentReference.exists()) {
                        Materia materia = documentReference.toObject(Materia.class);

                        materia.setLastFecha((int) (new Date()).getTime());
                        materia.setnEntradas(materia.getnEntradas() + 1);

                        getRefCollectionAllMaterias().document(materia.getName()).set(materia).addOnCompleteListener((task) -> {
                            if(task.isSuccessful()){
                                crearTemaDatabase(temaString, materiaTema, onCompleteListenerTema);
                            }else{
                                onCompleteListenerTema.onLoadTema(null);
                            }
                        });

                    } else {
                        String uid = UUID.randomUUID().toString();
                        long date = (new Date()).getTime();
                        //String id, String name, String imagen, String lastFecha
                        Materia materia = new Materia(uid, temaString, date, 1);
                        getRefCollectionAllMaterias().document(temaString).set(materia).addOnCompleteListener((task) -> {
                            if (task.isSuccessful()) {
                                crearTemaDatabase(materia.getName(), materiaTema, onCompleteListenerTema);
                            } else {
                                onCompleteListenerTema.onLoadTema(null);
                            }
                        });
                    }
                } else {
                    onCompleteListenerTema.onLoadTema(null);
                }
            });

        });
    }

    public void createMateriaTutor(String materiaName, MateriaTutor materiaTutor, OnCompleteListenerMateriaTutor onCompleteListenerMateriaTutor){
        activity.runOnUiThread(()->{
            getRefCollectionAllSolicitudes(materiaName)
                    .document(materiaTutor.getPublicacionId())
                    .collection(DBROUTES.MATERIAS_OFRECIMIENTOS)
                    .document(materiaTutor.getId())
                    .set(materiaTutor).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            DocumentReference notificacionAutorDatabase =  this.databaseNotificacion
                                    .getRefCollectionAllNotificaciones(materiaTutor.getAutorId())
                                    .document(materiaTutor.getPublicacionId());

                            notificacionAutorDatabase.get().addOnCompleteListener(taskAutor -> {
                                if(taskAutor.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = taskAutor.getResult();
                                    if(documentSnapshot.exists()){
                                        Notificacion notificacionAutor = documentSnapshot.toObject(Notificacion.class);
                                        notificacionAutor.setDescripcion("Alguien quiere ser tu tutor");
                                        notificacionAutorDatabase.set(notificacionAutor).addOnCompleteListener(taskAutorUpdate -> {
                                            if(taskAutorUpdate.isSuccessful()){

                                                String id = materiaTutor.getPublicacionId();
                                                String type = DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR_DAR;
                                                String refId = materiaTutor.getId();

                                                List<String> dirDatabase = new ArrayList<>();
                                                dirDatabase.add(DBROUTES.MATERIAS);
                                                dirDatabase.add(materiaName);
                                                dirDatabase.add(DBROUTES.MATERIAS_SOLUCITUDES);
                                                dirDatabase.add(materiaTutor.getAutorId());

                                                String title = materiaTutor.getDescripcion();
                                                String descripcion = "Te has ofrecido a ayudar";
                                                long fecha = new Date().getTime();

                                                //String id, String type, String refId,  List<String>  dirDatabase, String title, String descripcion, long fecha
                                                Notificacion notificacion = new Notificacion(id, type, refId, dirDatabase, title, descripcion, fecha);
                                                this.databaseNotificacion.createNotificacion(materiaTutor.getTutorId(), notificacion, (notificacionDatabase)->{
                                                    if(notificacionDatabase != null){
                                                        onCompleteListenerMateriaTutor.onLoadMateriaTutor(materiaTutor);

                                                    }else{
                                                        onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                                    }
                                                });
                                            }else{
                                                onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                            }
                                        });
                                    }else {
                                        onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                    }
                                }else{
                                    onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                }

                            });


                        }else{
                            onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                        }
            });

        });
    }

    private void crearTemaDatabase(String nameMateriaString, MateriaTema materiaTema, OnCompleteListenerTema onCompleteListenerTema) {
        getRefCollectionAllSolicitudes(nameMateriaString)
                .document(materiaTema.getId())
                .set(materiaTema).addOnCompleteListener((task) -> {
            if (task != null) {

                String id = materiaTema.getId();
                String type = DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR;
                String refId = materiaTema.getId();
                List<String> dirDatabase = new ArrayList<>();
                dirDatabase.add(DBROUTES.MATERIAS);
                dirDatabase.add(nameMateriaString);
                dirDatabase.add(DBROUTES.MATERIAS_SOLUCITUDES);
                dirDatabase.add(materiaTema.getId());
                String title = materiaTema.getDescripcion();
                String descripcion = "Tutor pendiente...";
                long fecha = new Date().getTime();

                //String id, String type, String refId, List<String>  dirDatabase, String title, String descripcion, long fecha
                Notificacion notificacion = new Notificacion(id, type, refId, dirDatabase, title, descripcion, fecha);
                this.databaseNotificacion.createNotificacion(materiaTema.getAutorId(), notificacion, (notificacionDatabase)->{
                    if(notificacionDatabase != null){
                        onCompleteListenerTema.onLoadTema(materiaTema);
                    }else{
                        onCompleteListenerTema.onLoadTema(null);
                    }
                });

            } else {
                onCompleteListenerTema.onLoadTema(null);
            }
        });
    }

    public void getAllMaterias(OnCompleteListenerAllMaterias onCompleteListenerAllMaterias) {

        activity.runOnUiThread(() -> {

            if (this.listenerMaterias != null) {
                this.stopListenerMaterias();
            }

            this.listenerMaterias = getRefCollectionAllMaterias().addSnapshotListener((value, error) -> {
                List<Materia> materias = value.toObjects(Materia.class);
                onCompleteListenerAllMaterias.onLoadAllMaterias(materias);
            });

        });
    }

    public void getNameUserId(String idUser, DatabaseUser.OnCompleteListenerUser onCompleteListenerUser){
        activity.runOnUiThread(() -> {
            DatabaseUser.getRefUserId(idUser, onCompleteListenerUser);
        });

    }

    public void getAllSolicitudes(String materiaId, OnCompleteListenerAllSoliticudes onCompleteListenerAllSoliticudes) {
        activity.runOnUiThread(() -> {
            if (this.listenerSolicitudes != null) {
                this.stopListenerSolicitudes();
            }

            this.listenerSolicitudes = getRefCollectionAllSolicitudes(materiaId).addSnapshotListener((value, error) -> {
                List<MateriaTema> materiaTemas = value.toObjects(MateriaTema.class);
                onCompleteListenerAllSoliticudes.onLoadAllSolicitudes(materiaTemas);
            });
        });
    }

    public void getSolicitudMateriaTema(String materiaId, String temaId, OnCompleteListenerTema onCompleteListenerTema, OnCompleteListenerMateriaTutores onCompleteListenerMateriaTutores){
        activity.runOnUiThread(()->{
            DocumentReference documentReferenceTema =  getRefCollectionAllSolicitudes(materiaId).document(temaId);
            documentReferenceTema.get().addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        onCompleteListenerTema.onLoadTema(documentSnapshot.toObject(MateriaTema.class));
                    }else{
                        onCompleteListenerTema.onLoadTema(null);
                    }
                }else{
                    onCompleteListenerTema.onLoadTema(null);
                }
            });
            documentReferenceTema.collection(DBROUTES.MATERIAS_OFRECIMIENTOS).get().addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                        onCompleteListenerMateriaTutores.onLoadMateriaTutor(querySnapshot.toObjects(MateriaTutor.class));
                }else{
                    onCompleteListenerMateriaTutores.onLoadMateriaTutor(null);
                }
            });
        });
    }

    public void stopListenerSolicitudes() {
        if (listenerSolicitudes != null) {
            listenerSolicitudes.remove();
            listenerSolicitudes = null;
        }
    }

    public void stopListenerMaterias() {
        if (listenerMaterias != null) {
            listenerMaterias.remove();
            listenerMaterias = null;
        }
    }

    private static CollectionReference getRefCollectionUserTutores(String userId) {
        return getRefCollectionAllMaterias()
                .document(userId)
                .collection(DBROUTES.MATERIAS_SOLUCITUDES);
    }

    private static CollectionReference getRefCollectionAllSolicitudes(String materiaId) {
        return getRefCollectionAllMaterias()
                .document(materiaId)
                .collection(DBROUTES.MATERIAS_SOLUCITUDES);
    }

    private static CollectionReference getRefCollectionAllMaterias() {
        CollectionReference collectionReference = FirebaseFirestore
                .getInstance()
                .collection(DBROUTES.MATERIAS);
        return collectionReference;
    }

    public interface OnCompleteListenerAllMaterias {
        void onLoadAllMaterias(List<Materia> materiaList);
    }

    public interface OnCompleteListenerAllSoliticudes {
        void onLoadAllSolicitudes(List<MateriaTema> materiaTemaList);
    }

    public interface OnCompleteListenerTema {
        void onLoadTema(MateriaTema materiaTema);
    }

    public interface OnCompleteListenerMateriaTutor{
        void onLoadMateriaTutor(MateriaTutor materiaTutor);
    }

    public interface OnCompleteListenerMateriaTutores{
        void onLoadMateriaTutor(List<MateriaTutor> materiaTutores);
    }



}
