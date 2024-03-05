package com.spotify.oauth2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.apiapplicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlists;
import com.spotify.oauth2.utils.DataLoader;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;

import static com.spotify.oauth2.utils.FakerUtils.*;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest {
	
	@Story("Create a playlist API")
	@Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
	@Description("Description for Create Playlist")
	@Test(description="should be able to create playlist")
	public void ShouldBeAbleToCreatePlaylist() {
		
		Playlists requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_201);
		assertPlaylistEqual(response.as(Playlists.class), requestPlaylist);
		
	}	
	
	@Test
	public void ShouldBeAbleToGetPlaylist() {
		
		Playlists requestPlaylist = playlistBuilder("Updated Selenium API Playlist Name", "Updated Selenium API playlist description", true);
		Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
		assertPlaylistEqual(response.as(Playlists.class), requestPlaylist);

	}	
	
	@Test
	public void ShouldBeAbleToUpdatePlaylist() {
		
		Playlists requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
		Response response = PlaylistApi.put(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
		
	}
	
	@Story("Create a playlist API")
	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithoutName() {
		
		Playlists requestPlaylist = playlistBuilder("", generateDescription(), false);		
		Response response = PlaylistApi.post(requestPlaylist);		
		assertStatusCode(response.statusCode(), StatusCode.CODE_400);		
		assertError(response.as(Error.class), StatusCode.CODE_400);
		
	}
	
	@Story("Create a playlist API")
	@Test
	public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken() {
		
		String invalid_token = "12345";		
		Playlists requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);		
		Response response = PlaylistApi.post(invalid_token, requestPlaylist);		
		assertStatusCode(response.statusCode(), StatusCode.CODE_401);		
		assertError(response.as(Error.class), StatusCode.CODE_401);
		
	}
	
	@Step
	public Playlists playlistBuilder(String name, String description, boolean _public) {
		
		return Playlists.builder().
				name(name).
				description(description).
				_public(_public).
				build();
		
	}
	
	@Step
	public void assertPlaylistEqual(Playlists responsePlaylists, Playlists requestPlaylist) {
		
		assertThat(responsePlaylists.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylists.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylists.get_public(), equalTo(requestPlaylist.get_public()));
		
	}
	
	@Step
	public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
		assertThat(actualStatusCode, equalTo(statusCode.code));
	}
	
	@Step
	public void assertError(Error responseErr, StatusCode statusCode) {
		
		assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
		assertThat(responseErr.getError().getMessage(), equalTo(statusCode.message));
		
	}



}
