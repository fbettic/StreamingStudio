package ar.edu.ubp.rest.portal.beans.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResponseMapperBean <T extends ServiceResponseBean> {
    Integer id;
    List<T> responseList;

    public ServiceResponseMapperBean(Integer id, List<T> responseList ){
        this.id = id;
        this.responseList = responseList;
    }
}
