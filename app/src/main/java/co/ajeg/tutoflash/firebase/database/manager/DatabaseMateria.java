package co.ajeg.tutoflash.firebase.database.manager;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;

public class DatabaseMateria {

    private static FragmentActivity activity;
    private static DatabaseMateria thisClass;
    private ListenerRegistration listenerSolicitudes;
    private ListenerRegistration listenerMaterias;

    private DatabaseMateria(FragmentActivity activity) {
        this.activity = activity;
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


                        materia.setLastFecha((new Date()).toString());
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
                        String date = (new Date()).toString();
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

    private void crearTemaDatabase(String temaString, MateriaTema materiaTema, OnCompleteListenerTema onCompleteListenerTema) {
        getRefCollectionAllSolicitudes(temaString)
                .document(materiaTema.getId())
                .set(materiaTema).addOnCompleteListener((task) -> {
            if (task != null) {
                onCompleteListenerTema.onLoadTema(materiaTema);
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


    private Map getImageDefaultMaterias(){
        Map<String, String> materias = new HashMap<>();

        return materias;
    }

}
