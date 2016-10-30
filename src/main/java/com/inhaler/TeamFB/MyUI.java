package com.inhaler.TeamFB;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import facebook4j.*;
import facebook4j.auth.AccessToken;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private static final String redirectURLFacebookLogin = "localhost:8080";

    public static Boolean authed = false;
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> clickEvent());
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
        Facebook fb = new FacebookFactory().getInstance();
        fb.setOAuthAppId("1825111607773045", "25028c7ec3578f40ac8ad3ce8d9a7c6e");
        //String commaSeparetedPermissions = "public_profile,user_friends,email,user_about_me,user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_actions:{app_namespace},user_birthday,user_education_history,user_events,user_games_activity,user_hometown,user_likes,user_location,user_managed_groups,user_photos,user_posts,user_relationships,user_relationship_details,user_religion_politics,user_tagged_places,user_videos,user_website,user_work_history,read_custom_friendlists,read_insights,read_audience_network_insights,read_page_mailboxes,manage_pages,publish_pages,publish_actions,rsvp_event,pages_show_list,pages_manage_cta,pages_manage_instant_articles,ads_read,ads_management,business_management,pages_messaging,pages_messaging_phone_number";
        String commaSeparetedPermissions = "public_profile,user_friends";
        fb.setOAuthPermissions(commaSeparetedPermissions );
        //fb.setOAuthAccessToken(new AccessToken(accessToken, null));
		try {
			AccessToken token = fb.getOAuthAppAccessToken();
			
			fb.setOAuthAccessToken(token);
			
			
			
			//ResponseList<Friendlist> friendList = fb.getFriendlists();
			//System.out.println(friendList);
			
			
			
		} catch (FacebookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//if (fb.getOAuthAccessToken()==null) {
		System.out.print("Token: "+fb.getOAuthAccessToken().getToken() + " type: " + fb.getOAuthAccessToken().getType());
		
		//fb.get
		
		
        vaadinRequest.getWrappedSession().setAttribute("facebook", fb);
        if (authed==false) {
        StringBuffer callbackURL = new StringBuffer("http:/"+vaadinRequest.getRemoteHost());
        System.out.println(vaadinRequest.getRemoteHost());
        int index = callbackURL.length()-1;//callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        //response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
        //System.out.println("Output: " + fb);
        getUI().getPage().setLocation(fb.getOAuthAuthorizationURL("https://www.localhost:8443/TeamFB/Callback"));
		//} else {
			System.out.print("Token: "+fb.getOAuthAccessToken().getToken() + " type: " + fb.getOAuthAccessToken().getType());
		//}
		} else {
			try {
				ResponseList<Friendlist> fl = fb.getFriendlists();
				System.out.println(fl);
			} catch (FacebookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        
        
        
    }

    private void clickEvent() {
    	//Page.getCurrent();
    	/*
    	String facebookURL = "https://www.facebook.com/v2.8/dialog/oauth?" +
    		  "client_id="+"1825111607773045"+
    		  "&redirect_uri="+"https:/localhost:8080";
    	getUI().getPage().setLocation(facebookURL);
    	//Facebook facebook = new FacebookFactory().getInstance();
        */
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
