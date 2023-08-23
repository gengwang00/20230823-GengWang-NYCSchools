package nyc.nycschool.network.api;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import nyc.nycschool.network.model.SatModel;
import nyc.nycschool.network.model.SchoolModel;
import retrofit2.http.GET;

public interface NycSchoolApiService {
    @GET("/resource/s3k6-pzi2.json/")
    Observable<List<SchoolModel>> getNycSchools();

    @GET("/resource/f9bf-2cp4.json/")
    Observable<List<SatModel>> getNycSchoolSats();
}
