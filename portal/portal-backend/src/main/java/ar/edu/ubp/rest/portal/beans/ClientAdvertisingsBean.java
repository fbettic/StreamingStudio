package ar.edu.ubp.rest.portal.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAdvertisingsBean {
    Integer advertisingId;
    List<AdvertisingResponseBean> advertisings;
}
