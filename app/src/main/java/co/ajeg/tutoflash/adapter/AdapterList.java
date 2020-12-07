package co.ajeg.tutoflash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterList<T> {

    private AdapterManager adaptador;
    private RecyclerView recyclerView;

    public AdapterList(RecyclerView recyclerView, List<T> arrayList, int layoutId, AdapterManagerList<T> result){
        this.recyclerView = recyclerView;
        this.adaptador = new AdapterManager(arrayList, layoutId, result);
        this.recyclerView.setAdapter(this.adaptador);
    }

    public void positionFinal(){
        if(this.recyclerView != null){
            this.recyclerView.getLayoutManager().scrollToPosition(this.adaptador.lista.size()-1);
        }
    }

    public ArrayList<T> getListActual(){
        return this.adaptador.lista;
    }

    public void onAddItem(T item){
        int position = this.adaptador.lista.size();
        this.adaptador.lista.add(position, item);
        this.adaptador.notifyItemInserted(position);
    }

    public void onAddItem(T item, int position){
        this.adaptador.lista.add(position, item);
        this.adaptador.notifyItemInserted(position);
    }

    public void onRemoveItem(T item) {
        int position = this.adaptador.lista.indexOf(item);
        if(position != -1){
            this.adaptador.lista.remove(position);
            this.adaptador.notifyItemRemoved(position);
        }
        if(this.recyclerView != null){
            this.recyclerView.setAdapter(this.adaptador);
        }
    }

    public void onRemoveItem(int position) {
        this.adaptador.lista.remove(position);
        this.adaptador.notifyItemRemoved(position);
        if(this.recyclerView != null){
            this.recyclerView.setAdapter(this.adaptador);
        }
    }

    public void onUpdateData(List<T> items) {
        this.adaptador.lista.clear();
        for(T o : items){
            this.adaptador.lista.add(o);
        }
        this.adaptador.notifyDataSetChanged();
        if(this.recyclerView != null){
            this.recyclerView.setAdapter(this.adaptador);
        }
    }

    public void onUpdateData(ArrayList<T> items) {
        this.adaptador.lista.clear();
        for(T o : items){
            this.adaptador.lista.add(o);
        }
        this.adaptador.notifyDataSetChanged();
        if(this.recyclerView != null){
            this.recyclerView.setAdapter(this.adaptador);
        }
    }

    public AdapterManager getAdapter(){
        return this.adaptador;
    }

    public class AdapterManager extends RecyclerView.Adapter<AdapterManager.ViewHolderDatos> {

        private ArrayList lista;
        private int layoutId;

        private AdapterManagerList result;

        public AdapterManager(List lista, int layoutId, AdapterManagerList result) {
            this.result = result;
            this.layoutId = layoutId;
            this.lista = new ArrayList();

            for(Object o : lista){
                this.lista.add(o);
            }
        }

        public AdapterManager(ArrayList lista, int layoutId, AdapterManagerList result) {
            this.result = result;
            this.layoutId = layoutId;
            this.lista =  new ArrayList();
            for(Object o : lista){
                this.lista.add(o);
            }
        }

        @NonNull
        @Override
        public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId,parent, false);
            return new ViewHolderDatos(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
            this.result.onChangeView(this.lista.get(position), holder.view, position);
        }

        @Override
        public int getItemCount() {
            return this.lista.size();
        }

        public class ViewHolderDatos extends RecyclerView.ViewHolder {
            private View view;

            public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                result.onCreateView(itemView);
            }
        }
    }
}
