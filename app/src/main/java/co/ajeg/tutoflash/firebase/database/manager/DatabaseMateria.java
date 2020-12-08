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

import co.ajeg.tutoflash.firebase.FirebaseMensajes;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatPerson;
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

    private List<Materia> materiaList;
    private OnCompleteListenerAllMaterias onCompleteListenerAllMaterias;

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

    private void deleteAllTutoresFromTema(String nameMateria,MateriaTema materiaTema, List<MateriaTutor> materiaTutorList, MateriaTutor tutor, ChatPerson chatPerson){

        String id = UUID.randomUUID().toString();
        String type = DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR_SELECIONADO;
        String refId = chatPerson.getId();
        List<String> dirDatabase = new ArrayList<>();
        dirDatabase.add(DBROUTES.CHATS);
        dirDatabase.add(chatPerson.getId());
        String title ="Tutor Aceptado";
        String descripcion = "Te han seleccionado para tutor";
        long fecha = new Date().getTime();

        //String id, String type, String refId, List<String> dirDatabase, String title, String descripcion, long fecha
        Notificacion notificacion = new Notificacion(id, type, refId, dirDatabase, title, descripcion, fecha);

        DatabaseNotificacion
                .getRefCollectionAllNotificaciones(tutor.getId())
                .document(notificacion.getId()).set(notificacion).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                    }else{

                    }
        });

        DatabaseNotificacion
                .getRefCollectionAllNotificaciones(materiaTema.getAutorId()).document(materiaTema.getId()).delete();



        getRefCollectionAllSolicitudes(nameMateria).document(materiaTema.getId()).delete();

       // DatabaseNotificacion.getRefCollectionAllNotificaciones(tutor.getTutorId()).

        for (MateriaTutor materiaTutor : materiaTutorList){
            if(materiaTutor.getId().equals(tutor.getId()) == false){
                FirebaseMensajes.sendNotificacion(tutor.getTutorId(), "Solicitur Eliminada", "Ya se ha seleccionado un tutor. Gracias por el ofrecimiento");
            }
            deteletMateriaTutor(nameMateria, materiaTema, materiaTutor.getTutorId(), (t)->{});
        }
    }

    public void seleccionarTutor(String nameMateria, MateriaTema materiaTema, MateriaTutor tutor, List<MateriaTutor> materiaTutorList,  OnCompleteListenerMateriaTutor onCompleteListenerMateriaTutor) {
        activity.runOnUiThread(() -> {
            //String id, String sujectAId, String sujectBId, String dateLast

            User user = Autenticacion.getUser();
            DocumentReference userDocumentRef = DatabaseUser.getMyRefUser();
            if (user != null && userDocumentRef != null) {
                userDocumentRef.collection(DBROUTES.USERS_CHATS).document(tutor.getTutorId()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseMensajes.sendNotificacion(tutor.getTutorId(), "Seleccionado", "Felicitaciones te han seleccionado como tutor");

                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            ChatPerson chatPerson = documentSnapshot.toObject(ChatPerson.class);

                            deleteAllTutoresFromTema(nameMateria, materiaTema, materiaTutorList, tutor, chatPerson);

                            onCompleteListenerMateriaTutor.onLoadMateriaTutor(tutor);

                        } else {
                            String uid = UUID.randomUUID().toString();
                            String sujectAId = user.getId();
                            String sujectBId = tutor.getTutorId();
                            long dateInit = new Date().getTime();
                            long dateLast = new Date().getTime();

                            //String id, String sujectAId, String sujectBId, long dateInit, long dateLast
                            ChatPerson chatPerson = new ChatPerson(uid, sujectAId, sujectBId, dateInit, dateLast);
                            DatabaseChat.getCollectionsChats().document(chatPerson.getId()).set(chatPerson).addOnCompleteListener((tareaChat) -> {
                                if (tareaChat.isSuccessful()) {

                                    DatabaseUser.getRefUser(tutor.getTutorId()).collection(DBROUTES.USERS_CHATS).document(user.getId()).set(chatPerson).addOnCompleteListener((chatFor) -> {
                                        if (chatFor.isSuccessful()) {
                                            DatabaseUser.getRefUser(user.getId()).collection(DBROUTES.USERS_CHATS).document(tutor.getTutorId()).set(chatPerson).addOnCompleteListener((chatFrom) -> {
                                                if (chatFrom.isSuccessful()) {

                                                    deleteAllTutoresFromTema(nameMateria, materiaTema, materiaTutorList, tutor, chatPerson);

                                                    onCompleteListenerMateriaTutor.onLoadMateriaTutor(tutor);
                                                } else {
                                                    onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                                }
                                            });
                                        } else {
                                            onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                        }
                                    });

                                } else {
                                    onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                }

                            });

                        }
                    } else {
                        onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                    }
                });
            } else {
                onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
            }
        });

        //  FirebaseFirestore.getInstance().collection(DBROUTES.CHATS).

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
                            if (task.isSuccessful()) {
                                crearTemaDatabase(temaString, materiaTema, onCompleteListenerTema);
                            } else {
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

    public void createMateriaTutor(String materiaName, MateriaTutor materiaTutor, OnCompleteListenerMateriaTutor onCompleteListenerMateriaTutor) {
        activity.runOnUiThread(() -> {
            getRefCollectionAllSolicitudes(materiaName)
                    .document(materiaTutor.getPublicacionId())
                    .collection(DBROUTES.MATERIAS_OFRECIMIENTOS)
                    .document(materiaTutor.getId())
                    .set(materiaTutor).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference notificacionAutorDatabase = this.databaseNotificacion
                            .getRefCollectionAllNotificaciones(materiaTutor.getAutorId())
                            .document(materiaTutor.getPublicacionId());

                    notificacionAutorDatabase.get().addOnCompleteListener(taskAutor -> {
                        if (taskAutor.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = taskAutor.getResult();
                            if (documentSnapshot.exists()) {
                                Notificacion notificacionAutor = documentSnapshot.toObject(Notificacion.class);
                                notificacionAutor.setDescripcion("Alguien quiere ser tu tutor");
                                notificacionAutorDatabase.set(notificacionAutor).addOnCompleteListener(taskAutorUpdate -> {
                                    if (taskAutorUpdate.isSuccessful()) {

                                        FirebaseMensajes.sendNotificacion(materiaTutor.getAutorId(), "Nuevo tutor", "Alguien se ha ofrecido a ayudar");

                                        String id = materiaTutor.getPublicacionId();
                                        String type = DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR_DAR;
                                        String refId = materiaTutor.getId();

                                        List<String> dirDatabase = new ArrayList<>();
                                        dirDatabase.add(DBROUTES.MATERIAS);
                                        dirDatabase.add(materiaName);
                                        dirDatabase.add(DBROUTES.MATERIAS_SOLUCITUDES);
                                        dirDatabase.add(materiaTutor.getPublicacionId());
                                        dirDatabase.add(DBROUTES.MATERIAS_OFRECIMIENTOS);
                                        dirDatabase.add(materiaTutor.getTutorId());

                                        String title = materiaTutor.getDescripcion();
                                        String descripcion = "Te has ofrecido a ayudar";
                                        long fecha = new Date().getTime();

                                        //String id, String type, String refId,  List<String>  dirDatabase, String title, String descripcion, long fecha
                                        Notificacion notificacion = new Notificacion(id, type, refId, dirDatabase, title, descripcion, fecha);
                                        this.databaseNotificacion.createNotificacion(materiaTutor.getTutorId(), notificacion, (notificacionDatabase) -> {
                                            if (notificacionDatabase != null) {
                                                onCompleteListenerMateriaTutor.onLoadMateriaTutor(materiaTutor);

                                            } else {
                                                onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                            }
                                        });
                                    } else {
                                        onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                                    }
                                });
                            } else {
                                onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                            }
                        } else {
                            onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                        }

                    });


                } else {
                    onCompleteListenerMateriaTutor.onLoadMateriaTutor(null);
                }
            });

        });
    }

    public void deteletMateriaTutor(String nameMateria, MateriaTema materiaTema, String tutorId, OnCompleteListenerTema onCompleteListenerTema){
        activity.runOnUiThread(()->{

            getRefCollectionAllSolicitudes(nameMateria).document(materiaTema.getId()).collection(DBROUTES.MATERIAS_OFRECIMIENTOS)
                    .document(tutorId).delete().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                            DatabaseNotificacion.getRefCollectionAllNotificaciones(tutorId).document(materiaTema.getId()).delete()
                                    .addOnCompleteListener((deleteNotificacion)->{
                                        if(deleteNotificacion.isSuccessful()){
                                            onCompleteListenerTema.onLoadTema(materiaTema);
                                        }else{
                                            onCompleteListenerTema.onLoadTema(null);
                                        }
                                    });
                        }else{
                            onCompleteListenerTema.onLoadTema(null);
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
                this.databaseNotificacion.createNotificacion(materiaTema.getAutorId(), notificacion, (notificacionDatabase) -> {
                    if (notificacionDatabase != null) {
                        onCompleteListenerTema.onLoadTema(materiaTema);
                    } else {
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
            this.onCompleteListenerAllMaterias = onCompleteListenerAllMaterias;

            if (this.listenerMaterias == null) {
                this.listenerMaterias = getRefCollectionAllMaterias().addSnapshotListener((value, error) -> {
                    List<Materia> materias = value.toObjects(Materia.class);
                    this.materiaList = materias;
                    if (this.onCompleteListenerAllMaterias != null) {
                        this.onCompleteListenerAllMaterias.onLoadAllMaterias(materias);
                    }
                });
            } else {
                if (this.materiaList != null) {
                    this.onCompleteListenerAllMaterias.onLoadAllMaterias(this.materiaList);
                }
            }
        });
    }

    public void getNameUserId(String idUser, DatabaseUser.OnCompleteListenerUser onCompleteListenerUser) {
        activity.runOnUiThread(() -> {
            DatabaseUser.getRefUserId(idUser, onCompleteListenerUser);
        });

    }

    private String materiasTemasId;
    private List<MateriaTema> materiasTemas;
    private OnCompleteListenerAllSoliticudes onCompleteListenerAllSoliticudes;

    public void getAllSolicitudes(String materiaId, OnCompleteListenerAllSoliticudes onCompleteListenerAllSoliticudes) {
        activity.runOnUiThread(() -> {
            this.onCompleteListenerAllSoliticudes = onCompleteListenerAllSoliticudes;

            if (this.materiasTemas == null || this.materiasTemasId.equals(materiaId) == false) {
                this.materiasTemasId = materiaId;

                if (this.listenerSolicitudes != null) {
                    this.stopListenerSolicitudes();
                }
                this.listenerSolicitudes = getRefCollectionAllSolicitudes(materiaId).addSnapshotListener((value, error) -> {
                    List<MateriaTema> materiasTemas = value.toObjects(MateriaTema.class);
                    this.materiasTemas = materiasTemas;
                    this.onCompleteListenerAllSoliticudes.onLoadAllSolicitudes(this.materiasTemas);
                });

            } else {
                if (this.materiasTemas != null) {
                    this.onCompleteListenerAllSoliticudes.onLoadAllSolicitudes(this.materiasTemas);
                }
            }

        });
    }

    public void getSolicitudMateriaTema(String materiaId, String temaId, OnCompleteListenerTema onCompleteListenerTema, OnCompleteListenerMateriaTutores onCompleteListenerMateriaTutores) {
        activity.runOnUiThread(() -> {
            DocumentReference documentReferenceTema = getRefCollectionAllSolicitudes(materiaId).document(temaId);
            if (onCompleteListenerTema != null) {
                documentReferenceTema.get().addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            onCompleteListenerTema.onLoadTema(documentSnapshot.toObject(MateriaTema.class));
                        } else {
                            onCompleteListenerTema.onLoadTema(null);
                        }
                    } else {
                        onCompleteListenerTema.onLoadTema(null);
                    }
                });
            }
            if (onCompleteListenerMateriaTutores != null) {
                documentReferenceTema.collection(DBROUTES.MATERIAS_OFRECIMIENTOS).get().addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        onCompleteListenerMateriaTutores.onLoadMateriaTutor(querySnapshot.toObjects(MateriaTutor.class));
                    } else {
                        onCompleteListenerMateriaTutores.onLoadMateriaTutor(null);
                    }
                });
            }

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

    public interface OnCompleteListenerMateriaTutor {
        void onLoadMateriaTutor(MateriaTutor materiaTutor);
    }

    public interface OnCompleteListenerMateriaTutores {
        void onLoadMateriaTutor(List<MateriaTutor> materiaTutores);
    }


}
