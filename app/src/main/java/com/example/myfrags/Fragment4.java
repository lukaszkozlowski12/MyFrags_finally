package com.example.myfrags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class Fragment4 extends Fragment {
    //1. pola do podlaczenia danych
    private FragsData fragsData;
    private Observer<Integer> numberObserver;

    //2.  pola do obslugi pola edit
    private EditText edit;
    private TextWatcher textWatcher;
    private boolean turnOffWatcher;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_4, container, false);


        //1 dostep do pola edit
        edit = view.findViewById(R.id.editTextNumber);

        //2 Pobranie obiektu ViewModel
        fragsData = new ViewModelProvider(requireActivity()).get(FragsData.class);

        //3 Stworzenie obserwatora // tutaj dla obiektu integer
numberObserver = new Observer<Integer>() {
    @Override
    // ta metoda uruchomi sie zawsze gdy bedzie zmiana
    public void onChanged(Integer integer) {

        turnOffWatcher=true;
        edit.setText(integer.toString());

    }
};

//4  podłączenie obserwatora do naszej zmiennej ktora chcemy podgladac
        fragsData.counter.observe(getViewLifecycleOwner(), numberObserver);

        //5 Stworzenie obiektu textWatchera
        //(odczytujemy tekst i konwertujemy na liczbe
        textWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!turnOffWatcher){
                    Integer i;
                    Boolean spr=false;
                    try {
                        i=Integer.parseInt(s.toString());
                        spr=true;
                    }catch (NumberFormatException e){
                        i=fragsData.counter.getValue();
                    }
                    if(!(s.length()==0 || (s.length()==1 && !spr)))
                    {
                        fragsData.counter.setValue(i);
                    edit.setSelection(edit.getText().length());

                    }

                }else { turnOffWatcher=!turnOffWatcher;}

            }
        };

        //6 podlaczenie TextWatchera do naszego pola edit
        edit.addTextChangedListener(textWatcher);

        return view;
    }
}