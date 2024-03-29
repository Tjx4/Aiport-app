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
import co.za.dvt.airportapp.helpers.ConverterHelper;
import co.za.dvt.airportapp.models.DepartureFlightsModel;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    protected List<DepartureFlightsModel> flights;
    protected LayoutInflater mInflater;
    protected FlightsAdapter.ItemClickListener mClickListener;

    public FlightsAdapter(Context context, List<DepartureFlightsModel> flights) {
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
        String airlineName = flights.get(position).getAirline().getName();
        String departureTime = ConverterHelper.getSimpleTime(flights.get(position).getDeparture().getScheduledTime());
        String flightNumber = flights.get(position).getFlight().getIataNumber();
        String destination = flights.get(position).getArrival().getIataCode(); // Make call for destination
        boolean isDeparted = flights.get(position).getStatus().equals("active");

        holder.airlineNameTv.setText(airlineName);
        holder.departureTimeTv.setText(departureTime);
        holder.destinationTv.setText(destination);
        holder.flightNumberTv.setText(flightNumber);

        if(isDeparted){
            holder.departureDestinationTv.setText(R.string.departed);
            holder.departureDestinationIndicatorImg.setImageResource(R.drawable.red_dot);
        }
        else{
            holder.departureDestinationTv.setText(R.string.boarding);
            holder.departureDestinationIndicatorImg.setImageResource(R.drawable.green_dot);
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
            DepartureFlightsModel flight = flights.get(getAdapterPosition());
            mClickListener.onItemClick(view, flight);
        }
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    DepartureFlightsModel getItem(int index) {
        return flights.get(index);
    }

    public void setClickListener(FlightsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, DepartureFlightsModel flight);
    }

}
