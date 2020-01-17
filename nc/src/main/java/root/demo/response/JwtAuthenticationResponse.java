package root.demo.response;

import root.demo.dto.ProfileDTO;

public class JwtAuthenticationResponse {
	
	private ProfileDTO profile;
    private String accessToken;

    public JwtAuthenticationResponse(ProfileDTO profile, String accessToken) {
    	this.profile = profile;
        this.accessToken = accessToken;
    }
    
    public ProfileDTO getProfile() {
		return profile;
	}
    
    public void setProfile(ProfileDTO profile) {
		this.profile = profile;
	}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
}