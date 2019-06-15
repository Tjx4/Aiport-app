package co.za.dvt.airportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.models.FlightModel;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    protected List<FlightModel> flights;
    protected LayoutInflater mInflater;
    protected FlightsAdapter.ItemClickListener mClickListener;

    public FlightsAdapter(Context context, List<FlightModel> flights) {
        this.mInflater = LayoutInflater.from(context);
        this.flights = flights;
    }

    @Override
    public FlightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.flight_layout, parent, false);
        return new FlightsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlightsAdapter.ViewHolder holder, int position) {
        String airlineName = flights.get(position).getAirlineName();
        String departureTime = flights.get(position).getDepartureTime();
        String destination = flights.get(position).getDestination();
        String flightNumber = flights.get(position).getFlightNumber();
        boolean isDeparted = flights.get(position).isDeperted();

        holder.airlineNameTv.setText(airlineName);
        holder.departureTimeTv.setText(departureTime);
        holder.destinationTv.setText(destination);
        holder.flightNumberTv.setText(flightNumber);

        if(isDeparted){
            holder.departureDestinationTv.setText(R.string.departed);
            holder.departureDestinationIndicatorImg.setImageResource(R.drawable.red_dot_x2);
        }
        else{
            holder.departureDestinationTv.setText(R.string.boarding);
            holder.departureDestinationIndicatorImg.setImageResource(R.drawable.green_dot_x2);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView airlineNameTv;
        TextView departureTimeTv;
        TextView destinationTv;
        TextView flightNumberTv;
        TextView departureDestinationTv;
        ImageView departureDestinationIndicatorImg;

        ViewHolder(View itemView) {
            super(itemView);
            airlineNameTv = itemView.findViewById(R.id.tvAirportName);
            departureTimeTv = itemView.findViewById(R.id.tvDepartureTime);
            destinationTv = itemView.findViewById(R.id.tvDestination);
            flightNumberTv = itemView.findViewById(R.id.tvFlightNumber);
            departureDestinationTv = itemView.findViewById(R.id.tvDepartureDestination);
            departureDestinationIndicatorImg = itemView.findViewById(R.id.imgDepartureDestinationIndicator);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FlightModel flight = flights.get(getAdapterPosition());
            mClickListener.onItemClick(view, flight);
        }
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    FlightModel getItem(int index) {
        return flights.get(index);
    }

    public void setClickListener(FlightsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, FlightModel flight);
    }

}
