package ar.edu.ubp.rest.portal.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IStreamingPlatformRepository;

@Repository
public class StreamingPlatformRepository implements IStreamingPlatformRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @SuppressWarnings("unchecked")
        @Override
        public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO streamingPlatfromRequest) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("platformName", streamingPlatfromRequest.getPlatformName())
                                .addValue("email", streamingPlatfromRequest.getEmail())
                                .addValue("apiUrl", streamingPlatfromRequest.getApiUrl())
                                .addValue("authToken", streamingPlatfromRequest.getAuthToken())
                                .addValue("signupFeeId", streamingPlatfromRequest.getSignupFeeId())
                                .addValue("loginFeeId", streamingPlatfromRequest.getLoginFeeId())
                                .addValue("serviceType", streamingPlatfromRequest.getServiceType());

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("CreateStreamingPlatform")
                                .withSchemaName("dbo")
                                .returningResultSet("platform",
                                                BeanPropertyRowMapper.newInstance(StreamingPlatformDTO.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<StreamingPlatformDTO>) output.get("platform")).get(0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public StreamingPlatformDTO getStreamingPlatformById(Integer id) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("platformId", id);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("GetStreamingPlatformById")
                                .withSchemaName("dbo")
                                .returningResultSet("platform",
                                                BeanPropertyRowMapper.newInstance(StreamingPlatformDTO.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<StreamingPlatformDTO>) output.get("platform")).get(0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<StreamingPlatformDTO> getAllStreamingPlatfroms() {
                SqlParameterSource input = new MapSqlParameterSource();

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("GetAllStreamingPlatforms")
                                .withSchemaName("dbo")
                                .returningResultSet("platforms",
                                                BeanPropertyRowMapper.newInstance(StreamingPlatformDTO.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return (List<StreamingPlatformDTO>) output.get("platforms");
        }

        @SuppressWarnings("unchecked")
        @Override
        public Integer deleteStreamingPlatfromById(Integer id) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("tableName", "StreamingPlatform")
                                .addValue("primaryKeyColumn", "platformId")
                                .addValue("primaryKeyValue", id);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("SoftDeleteRecord")
                                .withSchemaName("dbo")
                                .returningResultSet("platformId", BeanPropertyRowMapper.newInstance(Integer.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<Integer>) output.get("platformId")).get(0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public StreamingPlatformDTO updateStreamingPlatform(StreamingPlatformRequestDTO streamingPlatfromRequest,
                        Integer id) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("platformName", streamingPlatfromRequest.getPlatformName())
                                .addValue("email", streamingPlatfromRequest.getEmail())
                                .addValue("apiUrl", streamingPlatfromRequest.getApiUrl())
                                .addValue("authToken", streamingPlatfromRequest.getAuthToken())
                                .addValue("signupFeeId", streamingPlatfromRequest.getSignupFeeId())
                                .addValue("loginFeeId", streamingPlatfromRequest.getLoginFeeId())
                                .addValue("serviceType", streamingPlatfromRequest.getServiceType())
                                .addValue("platformId", id);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("UpdateStreamingPlatform")
                                .withSchemaName("dbo")
                                .returningResultSet("platform",
                                                BeanPropertyRowMapper.newInstance(StreamingPlatformDTO.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<StreamingPlatformDTO>) output.get("platform")).get(0);
        }
}
