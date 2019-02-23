package ca.ulaval.ima.tp2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment implements View.OnClickListener {
    private final Calendar cal = Calendar.getInstance();
    private boolean calStart = false;
    private final Calendar calToday = Calendar.getInstance();
    IdentityParcelable identity = new IdentityParcelable();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.identity = bundle.getParcelable("identity");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void submitForm() {
        final EditText first_name = view.findViewById(R.id.first_name);
        final EditText last_name = view.findViewById(R.id.last_name);
        final EditText date = view.findViewById(R.id.date);
        final RadioGroup radioSexe = view.findViewById(R.id.radioSexe);
        final RadioButton sexe = view.findViewById(radioSexe.getCheckedRadioButtonId());
        final Spinner prog = view.findViewById(R.id.prog);
        final Intent intent = new Intent(getActivity(), ProfileActivity.class);

        if (first_name.getText().toString().length() < 1) {
            first_name.setError("Prénom invalide");
            return;
        }
        if (last_name.getText().toString().length() < 1) {
            last_name.setError("Nom invalide");
            return;
        }
        identity.prog =  prog.getSelectedItem().toString();
        identity.sexe = sexe.getText().toString();
        identity.lastName = last_name.getText().toString();
        identity.date = date.getText().toString();
        identity.firstName = first_name.getText().toString();

        intent.putExtra("identity", identity);
        getActivity().startActivity(intent);
    }

    public void handleDate(final View v) {
        if (!calStart) {
            cal.set(1996, 9, 2, 1, 1, 1);
            calStart = true;
        }
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String placeHolderDate;
                String mounthSeparator = "-";
                String daySeparator = "-";
                if (monthOfYear < 10) {
                    mounthSeparator = "-0";
                }
                if (dayOfMonth < 10) {
                    daySeparator = "-0";
                }
                placeHolderDate = year + mounthSeparator + (monthOfYear + 1) + daySeparator + dayOfMonth;

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((EditText) v).setText(placeHolderDate);
            }

        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                dateListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(calToday.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.date:
                handleDate(v);
                break;
            case R.id.form_submit_btn:
                submitForm();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view =  inflater.inflate(R.layout.fragment_form, container, false);

        final EditText txtDate = view.findViewById(R.id.date);
        txtDate.setOnClickListener(this);

        final Button btn_submit = view.findViewById(R.id.form_submit_btn);
        btn_submit.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
