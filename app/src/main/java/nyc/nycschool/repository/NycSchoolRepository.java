package nyc.nycschool.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import nyc.nycschool.network.api.NycSchoolApiService;
import nyc.nycschool.network.model.SatModel;
import nyc.nycschool.network.model.SchoolModel;

public class NycSchoolRepository {
    private final NycSchoolApiService schoolApiService;

    private List<SatModel> satScoreList = new ArrayList();

    @Inject
    NycSchoolRepository(NycSchoolApiService schoolApiService) {
        this.schoolApiService = schoolApiService;
    }

    public Observable<List<SchoolModel>> getNycSchools() {
        return schoolApiService.getNycSchools();
    }

    public Observable<SatModel> getNycSchoolSats(String searchId) {
        return getSatList()
                .flatMapIterable(list -> list)
                .filter(myModel -> myModel.getId().equalsIgnoreCase(searchId));
    }

    private Observable<List<SatModel>> getSatList() {
        if (satScoreList.isEmpty()) {
            return schoolApiService.getNycSchoolSats()
                    .doOnNext(list -> satScoreList.addAll(list));
        } else {
            return Observable.just(satScoreList);
        }
    }
}
