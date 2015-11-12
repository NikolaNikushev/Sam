package fontys.fhict.delta.sam;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends BaseAdapter{
    String [] result;
    public static List<Switch> errorSwitches = new ArrayList<Switch>();

    Context context;
    public static boolean hasActive = false;
    private static LayoutInflater inflater=null;
    public ProjectsAdapter(MainMenu mainActivity, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context= mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView project;
        Chronometer counter;
        Switch aSwitch;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.project_item, null);
        holder.project=(TextView) rowView.findViewById(R.id.projects_name_project);
        holder.counter=(Chronometer) rowView.findViewById(R.id.projects_timer_counter);
        holder.counter.stop();
        holder.counter.setBase(SystemClock.elapsedRealtime());
        holder.aSwitch=(Switch) rowView.findViewById(R.id.projects_timer_activator);
        holder.project.setText(result[position]);
        holder.aSwitch.setChecked(false);

        holder.counter.setVisibility(View.INVISIBLE);
        holder.aSwitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch me = (Switch) view;
                if (me.isChecked()) {
                    if (hasActive) {
                        me.setError("Finish your other project!");
                        errorSwitches.add((me));
                        me.setChecked(false);
                        return;
                    }
                    holder.counter.setVisibility(View.VISIBLE);
                    // makes sure the timer is 0
                    holder.counter.setBase(SystemClock.elapsedRealtime());
                    holder.counter.start();
                    hasActive = true;

                } else {
                    holder.counter.setVisibility(View.INVISIBLE);
                    holder.counter.stop();
                    hasActive = false;
                    for (Switch a: errorSwitches
                         ) {
                        a.setError(null);
                    }

                    //// TODO: 12-Nov-15 record value

                    // This resets the counter
                    holder.counter.setBase(SystemClock.elapsedRealtime());

                }
            }
        });
        return rowView;
    }

}
