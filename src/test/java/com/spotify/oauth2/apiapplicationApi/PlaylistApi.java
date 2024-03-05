package com.spotify.oauth2.apiapplicationApi;

import static com.spotify.oauth2.api.TokenManager.getToken;
import static com.spotify.oauth2.api.Route.*;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlists;
import com.spotify.oauth2.utils.ConfigLoader;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlaylistApi {
	
	@Step
	public static Response post(Playlists requestPlaylist) {
		
		return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS
				, getToken(), requestPlaylist);
		
	}
	
	public static Response post(String token, Playlists requestPlaylist) {
		
		return RestResource.post(USERS + "/"+ ConfigLoader.getInstance().getUser() + PLAYLISTS
				, token, requestPlaylist);
		
	}
	
	public static Response get(String playlistId) {
		
		return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
		
	}
	
	public static Response put(String playlistId, Playlists requestPlaylist) {
		
		return RestResource.put(PLAYLISTS + "/"+ playlistId, getToken(), requestPlaylist);
		
	}

}
