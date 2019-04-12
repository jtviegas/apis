package org.aprestos.labs.apis.springboot2.statemanager;

import lombok.extern.slf4j.Slf4j;
import org.aprestos.labs.apiclient.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class StateManagerImpl<I,O,S> implements org.aprestos.labs.apis.springboot2.statemanager.StateManager<I,O,S> {

	@Autowired
	private RestClient client;

	@Value("${org.aprestos.labs.apis.springboot2.statemanager.uri.collective}")
	private String collectiveUri;
	@Value("${org.aprestos.labs.apis.springboot2.statemanager.uri.singular}")
	private String singularUri;

	@Override
	public void notify(O state) throws org.aprestos.labs.apis.springboot2.statemanager.StateManagerException {
		log.trace("[notify|in] state: {}", state);
		try {
			MultiValueMap<String, String> header = client.getHeadersBuilder().build();
			client.post(state, header, collectiveUri);
			log.info("[notify] posted status: {}", state);
		} catch (Exception e) {
			throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(e);
		} finally {
			log.trace("[notify|out]");
		}
	}

	@Override
	public Optional<S> getStatus(I id) throws org.aprestos.labs.apis.springboot2.statemanager.StateManagerException {
		log.trace("[getStatus|in] id: {}", id);
		Optional<S> result = null;
		try {
			MultiValueMap<String, String> header = client.getHeadersBuilder().build();
			result = client.get(new ParameterizedTypeReference<S>() {
			}, header, singularUri, null, id);
			return result;
		} catch (HttpClientErrorException hce) {
			if (hce.getStatusCode().equals(HttpStatus.NOT_FOUND))
				throw new org.aprestos.labs.apis.springboot2.statemanager.StateNotFoundException(hce);
			else
				throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(hce);
		} catch (Exception e) {
			throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(e);
		} finally {
			log.trace("[getStatus|out] {}", result);
		}
	}

	@Override
	public Map<Long, S> getAllStatus() throws org.aprestos.labs.apis.springboot2.statemanager.StateManagerException {
		log.trace("[getAllStatus|in]");
		Map<Long,S> result = null;
		try {
			MultiValueMap<String, String> header = client.getHeadersBuilder().build();
			result = client.get(new ParameterizedTypeReference<Map<Long,S>>() {
			}, header, collectiveUri, null, (Object) null).get();
			return result;
		} catch (Exception e) {
			throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(e);
		} finally {
			log.trace("[getAllStatus|out] {}", result);
		}
	}

	@Override
	public Optional<O> getState(I id) throws org.aprestos.labs.apis.springboot2.statemanager.StateManagerException {
		log.trace("[getState|in] id: {}", id);
		Optional<O> result = null;
		try {
			MultiValueMap<String, String> header = client.getHeadersBuilder().build();
			result = client.get(new ParameterizedTypeReference<O>() {
			}, header, singularUri, null, id);
			return result;
		} catch (HttpClientErrorException hce) {
			if (hce.getStatusCode().equals(HttpStatus.NOT_FOUND))
				throw new org.aprestos.labs.apis.springboot2.statemanager.StateNotFoundException(hce);
			else
				throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(hce);
		} catch (Exception e) {
			throw new org.aprestos.labs.apis.springboot2.statemanager.StateManagerException(e);
		} finally {
			log.trace("[getState|out] {}", result);
		}
	}


}
