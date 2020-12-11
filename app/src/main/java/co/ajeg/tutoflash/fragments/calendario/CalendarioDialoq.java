package co.ajeg.tutoflash.fragments.calendario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import co.ajeg.tutoflash.R;

public class CalendarioDialoq extends AppCompatDialogFragment {
    private EditText descripcion;
    private EditText fecha;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregaritem_calendario,null);

        builder.setView(view)
                .setTitle("Nueva tutoria")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        descripcion= view.findViewById(R.id.et_dialog_calendario_descripcion);
        fecha= view.findViewById(R.id.et_dialog_calendario_fecha);
        return builder.create();
    }

    public interface CalendarioDialogListener{

        void applyTexts(String descripcion, String fecha);


    }
}
