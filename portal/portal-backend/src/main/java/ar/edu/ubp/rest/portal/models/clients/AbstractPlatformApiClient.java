package ar.edu.ubp.rest.portal.models.clients;

import java.util.List;

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.UserPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import lombok.Data;

@Data
public abstract class AbstractPlatformApiClient {
    protected String url = "";

    public abstract String ping(ServicePayloadBean payload);

    public abstract List<FilmResponseBean> getAllFilms(ServicePayloadBean payload);

    public abstract AssociationResponseBean createAssociationRequest(AssociationRequestPayloadBean payload);

    public abstract AssociationResponseBean getAssociationData(AssociationPayloadBean payload);

    public abstract AssociationResponseBean cancelAssociationRequest(UserPayloadBean payload);

    public abstract SessionResponseBean createSession(SessionPayloadBean payload);

    public abstract BasicResponseBean sendWeeklyReport(WeeklyPlatformReportPayloadBean payload);

}
