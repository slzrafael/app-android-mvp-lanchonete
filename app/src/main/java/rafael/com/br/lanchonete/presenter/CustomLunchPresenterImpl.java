package rafael.com.br.lanchonete.presenter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import rafael.com.br.lanchonete.adapter.IngredientAdapter;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.service.BaseRequestCallback;
import rafael.com.br.lanchonete.service.IngredientService;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.view.CustomLunchView;

/**
 * Created by rafael-iteris on 23/08/17.
 */

public class CustomLunchPresenterImpl implements CustomLunchPresenter, IngredientAdapter.OnIngredientModifierListener {

    private LunchService lunchService;
    private IngredientService ingredientService;

    private CustomLunchView view;
    private AtomicInteger counter = new AtomicInteger(0);

    private Order order = new Order();

    public CustomLunchPresenterImpl(LunchService lunchService, IngredientService ingredientService) {
        this.lunchService = lunchService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void getLunchInfo(Integer id) {
        lunchService.getInfoOfLunch(id, new BaseRequestCallback<Lunch, RuntimeException>() {

            @Override
            public void onSuccess(Lunch result) {
                view.showInfoLunch(result);

                order.setLunch(result);
            }

            @Override
            public void onErro(RuntimeException err) {
                view.showMessageOfError("Não foi possivel buscar as informações do seu lanche.");
            }

            @Override
            public void onStart() {
                counter.incrementAndGet();
                view.onShowLoading();
            }

            @Override
            public void onEnd() {
                if(counter.decrementAndGet() == 0)
                    view.onDismissLoading();
            }

        });
    }

    @Override
    public void getListOfIngredients() {
        ingredientService.getListOfIngredients(new BaseRequestCallback<List<Ingredient>, RuntimeException>() {

            @Override
            public void onSuccess(List<Ingredient> result) {
                view.showListOfIngredients(result);
            }

            @Override
            public void onErro(RuntimeException err) {
                view.showMessageOfError("Não foi possivel listar os ingredientes.");
            }

            @Override
            public void onStart() {
                counter.incrementAndGet();
                view.onShowLoading();
            }

            @Override
            public void onEnd() {
                if(counter.decrementAndGet() == 0)
                    view.onDismissLoading();
            }

        });
    }

    @Override
    public void increase(int times, Ingredient ingredient) {
        for (int i = 0; i < times; i++) order.addIngredient(ingredient);

        view.showTotalPrice(order.getFinalPrice());
    }

    @Override
    public void decrease(int times, Ingredient ingredient) {
        for (int i = 0; i < times; i++) order.removeIngredient(ingredient);

        view.showTotalPrice(order.getFinalPrice());
    }

    public CustomLunchView getView() {
        return view;
    }

    public void setView(CustomLunchView view) {
        this.view = view;
    }

    public Order getOrder() {
        return order;
    }

}