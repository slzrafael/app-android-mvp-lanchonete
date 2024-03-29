package rafael.com.br.lanchonete.service;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.PromoResponseVO;
import rafael.com.br.lanchonete.model.Promo;

/**
 * Created by rafael-iteris on 18/08/17.
 */

public class PromoServiceRESTImpl implements PromoService {

    private API api;

    public PromoServiceRESTImpl() {}

    public PromoServiceRESTImpl(API api) {
        this.api = api;
    }

    @Override
    public void getListOfPromos(BaseRequestCallback<List<Promo>, RuntimeException> callback) {
        callback.onStart();

        request().onErrorResumeNext(error(callback)).subscribe(success(callback));
    }

    public Observable<List<PromoResponseVO>> request(){
        return api.getPromos()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Function<Throwable, ObservableSource<? extends List<PromoResponseVO>>> error(final BaseRequestCallback<List<Promo>, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends List<PromoResponseVO>>>() {

            @Override
            public ObservableSource<? extends List<PromoResponseVO>> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Ocorreu um erro ao buscar pelas promoções."));
                callback.onEnd();

                return Observable.empty();
            }

        };
    }

    public Consumer<List<PromoResponseVO>> success(final BaseRequestCallback<List<Promo>, RuntimeException> callback){
        return new Consumer<List<PromoResponseVO>>() {

            @Override
            public void accept(List<PromoResponseVO> result) throws Exception {
                List<Promo> promos = new ArrayList<>();

                for (PromoResponseVO vo : result){
                    Promo promo = new Promo(vo.id, vo.name, vo.description);
                    promos.add(promo);
                }

                callback.onSuccess(promos);
                callback.onEnd();
            }

        };
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

}
