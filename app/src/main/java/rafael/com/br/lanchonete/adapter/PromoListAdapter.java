package rafael.com.br.lanchonete.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.model.Promo;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.ItemViewHolder> {

    private List<Promo> promos;

    public PromoListAdapter(List<Promo> promos) {
        this.promos = promos;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflated = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo_view, parent, false);
        return new ItemViewHolder(inflated);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Promo promo = promos.get(position);

        holder.title.setText(promo.getName());
        holder.description.setText(promo.getDescription());
    }

    @Override
    public int getItemCount() {
        return promos.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titulo)
        TextView title;

        @BindView(R.id.descricao)
        TextView description;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
