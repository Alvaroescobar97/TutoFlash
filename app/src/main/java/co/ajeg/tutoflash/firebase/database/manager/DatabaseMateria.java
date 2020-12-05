package co.ajeg.tutoflash.firebase.database.manager;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;

public class DatabaseMateria {

    private static FragmentActivity activity;
    private static DatabaseMateria thisClass;
    private ListenerRegistration listenerSolicitudes;

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

    public void createTema(Materia materia, MateriaTema materiaTema, OnCompleteListenerTema onCompleteListenerTema){
        activity.runOnUiThread(()->{
            String temaString = materia.getName().trim().replaceAll(" ", "").toLowerCase();
            getRefCollectionAllMaterias().document(temaString).get().addOnCompleteListener(result -> {
                if(result.isSuccessful()){
                    if(result.getResult().exists()){
                        crearTemaDatabase(materia.getName(), materiaTema, onCompleteListenerTema);
                    }else{
                        getRefCollectionAllMaterias().document(temaString).set(materia).addOnCompleteListener((task)->{
                            if(task.isSuccessful()){
                                crearTemaDatabase(materia.getName(), materiaTema, onCompleteListenerTema);
                            }else{
                                onCompleteListenerTema.onLoadTema(null);
                            }
                        });
                    }
                }else{
                    onCompleteListenerTema.onLoadTema(null);
                }
            });

        });
    }

    private void crearTemaDatabase(String temaString, MateriaTema materiaTema, OnCompleteListenerTema onCompleteListenerTema){
        getRefCollectionAllSolicitudes(temaString)
                .document(materiaTema.getId())
                .set(materiaTema).addOnCompleteListener((task)->{
            if(task != null){
                onCompleteListenerTema.onLoadTema(materiaTema);
            }else{
                onCompleteListenerTema.onLoadTema(null);
            }
        });
    }

    public void getAllMaterias(OnCompleteListenerAllMaterias onCompleteListenerAllMaterias) {
        activity.runOnUiThread(() -> {
            getRefCollectionAllMaterias().addSnapshotListener((value, error) -> {
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

    public interface OnCompleteListenerTema{
        void onLoadTema(MateriaTema materiaTema);
    }


}
