package co.ajeg.tutoflash.adapter;

import android.view.View;

public interface AdapterManagerList<T> {

    void onCreateView(View v);

    void onChangeView(T elemnto, int position);

}
