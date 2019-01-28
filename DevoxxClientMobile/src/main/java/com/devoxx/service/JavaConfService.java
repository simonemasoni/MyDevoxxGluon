package com.devoxx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import com.devoxx.model.Badge;
import com.devoxx.model.Conference;
import com.devoxx.model.Exhibitor;
import com.devoxx.model.Favorite;
import com.devoxx.model.Feedback;
import com.devoxx.model.Floor;
import com.devoxx.model.Location;
import com.devoxx.model.Note;
import com.devoxx.model.RatingData;
import com.devoxx.model.Session;
import com.devoxx.model.SessionType;
import com.devoxx.model.Speaker;
import com.devoxx.model.Sponsor;
import com.devoxx.model.SponsorBadge;
import com.devoxx.model.Track;
import com.devoxx.model.Vote;
import com.devoxx.views.helper.SessionVisuals.SessionListType;
import com.gluonhq.cloudlink.client.user.User;
import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class JavaConfService implements Service {

	private Conference currentConference;
	
	public JavaConfService() {
		super();
		Conference conference = new Conference();
		conference.setId("test-conf-0");
		conference.setEventType(Conference.Type.MEETUP.name());
		conference.setName("JBCNConf Barcelona");
		conference.setFromDate("2019-01-20");
		conference.setEndDate("2019-01-21");
		conference.setImageURL("https://www.jbcnconf.com/2019/assets/img/logos/logo-jbcnconf.png");
		conference.setTimezone(TimeZone.getDefault().getID());
		List<Floor> floorPlans = new ArrayList<>();
		conference.setFloorPlans(floorPlans);
		currentConference = conference;
	}

	@Override
	public GluonObservableList<Conference> retrievePastConferences() {		
		GluonObservableList<Conference> conferences = new GluonObservableList<>();
		conferences.add(currentConference);
		return conferences;
	}

	@Override
	public GluonObservableList<Conference> retrieveConferences() {
		GluonObservableList<Conference> conferences = new GluonObservableList<>();
		conferences.add(currentConference);
		return conferences;
	}

	@Override
	public GluonObservableObject<Conference> retrieveConference(String conferenceId) {
		GluonObservableObject<Conference> goo = new GluonObservableObject<>();
		goo.set(currentConference);
		return goo;
	}

	@Override
	public void setConference(Conference selectedItem) {
		System.out.println("JavaConfService.setConference()");
	}

	@Override
	public Conference getConference() {		
		return currentConference;
	}

	@Override
	public ReadOnlyObjectProperty<Conference> conferenceProperty() {
		System.out.println("JavaConfService.conferenceProperty()");
		GluonObservableObject<Conference> obj = new GluonObservableObject<>();
		obj.set(currentConference);
		return obj;
	}

	@Override
	public void checkIfReloadRequested() {
		System.out.println("JavaConfService.checkIfReloadRequested()");
	}

	@Override
	public boolean showRatingDialog() {
		System.out.println("JavaConfService.showRatingDialog()");
		return false;
	}

	@Override
	public ReadOnlyListProperty<Session> retrieveSessions() {
		System.out.println("JavaConfService.retrieveSessions()");
		return new SimpleListProperty<>();
	}

	@Override
	public ReadOnlyListProperty<Speaker> retrieveSpeakers() {
		System.out.println("JavaConfService.retrieveSpeakers()");
		return new SimpleListProperty<>();
	}

	@Override
	public ReadOnlyObjectProperty<Speaker> retrieveSpeaker(String uuid) {
		System.out.println("JavaConfService.retrieveSpeaker()");
		return new GluonObservableObject<>();
	}

	@Override
	public ReadOnlyListProperty<Track> retrieveTracks() {
		System.out.println("JavaConfService.retrieveTracks()");
		return new SimpleListProperty<>();
	}

	@Override
	public ReadOnlyListProperty<SessionType> retrieveSessionTypes() {
		System.out.println("JavaConfService.retrieveSessionTypes()");
		return new SimpleListProperty<>();
	}

	@Override
	public ReadOnlyListProperty<Exhibitor> retrieveExhibitors() {
		System.out.println("JavaConfService.retrieveExhibitors()");
		return new SimpleListProperty<>();
	}

	@Override
	public ReadOnlyListProperty<Floor> retrieveExhibitionMaps() {
		System.out.println("JavaConfService.retrieveExhibitionMaps()");
		return new SimpleListProperty<>();
	}

	@Override
	public GluonObservableList<Sponsor> retrieveSponsors() {
		System.out.println("JavaConfService.retrieveSponsors()");
		return new GluonObservableList<>();
	}

	@Override
	public void authenticate(Runnable successRunnable) {
		System.out.println("JavaConfService.authenticate()");
		successRunnable.run();
	}

	@Override
	public void authenticate(Runnable successRunnable, Runnable failureRunnable) {
		System.out.println("JavaConfService.authenticate()");
		successRunnable.run();
	}

	@Override
	public boolean isAuthenticated() {
		System.out.println("JavaConfService.isAuthenticated()");
		return true;
	}

	@Override
	public BooleanProperty readyProperty() {
		System.out.println("JavaConfService.readyProperty()");
		SimpleBooleanProperty bp = new SimpleBooleanProperty();
		bp.set(true);
		return bp;
	}

	@Override
	public boolean logOut() {
		System.out.println("JavaConfService.logOut()");
		return true;
	}

	@Override
	public ObservableList<Session> retrieveFavoredSessions() {
		System.out.println("JavaConfService.retrieveFavoredSessions()");
		return new GluonObservableList<>();
	}

	@Override
	public ObservableList<Note> retrieveNotes() {
		System.out.println("JavaConfService.retrieveNotes()");
		return new GluonObservableList<>();
	}

	@Override
	public ObservableList<Badge> retrieveBadges() {
		System.out.println("JavaConfService.retrieveBadges()");
		return new GluonObservableList<>();
	}

	@Override
	public ObservableList<SponsorBadge> retrieveSponsorBadges(Sponsor sponsor) {
		System.out.println("JavaConfService.retrieveSponsorBadges()");
		return new GluonObservableList<>();
	}

	@Override
	public void logoutSponsor() {
		System.out.println("JavaConfService.logoutSponsor()");
	}

	@Override
	public ObservableList<Session> reloadSessionsFromCFP(SessionListType sessionListType) {
		System.out.println("JavaConfService.reloadSessionsFromCFP()");
		return new GluonObservableList<>();
	}

	@Override
	public Optional<Session> findSession(String uuid) {
		System.out.println("JavaConfService.findSession()");
		return Optional.of(new Session());
	}

	@Override
	public void voteTalk(Vote vote) {
		System.out.println("JavaConfService.voteTalk()");
	}

	@Override
	public ObservableList<Favorite> retrieveFavorites() {
		System.out.println("JavaConfService.retrieveFavorites()");
		return new GluonObservableList<>();
	}

	@Override
	public void refreshFavorites() {
		System.out.println("JavaConfService.refreshFavorites()");
	}

	@Override
	public GluonObservableObject<String> authenticateSponsor() {
		System.out.println("JavaConfService.authenticateSponsor()");
		return new GluonObservableObject<>();
	}

	@Override
	public void saveSponsorBadge(SponsorBadge sponsorBadge) {
		System.out.println("JavaConfService.saveSponsorBadge()");
	}

	@Override
	public User getAuthenticatedUser() {
		System.out.println("JavaConfService.getAuthenticatedUser()");
		return new User();
	}

	@Override
	public String getCfpUserUid() {
		System.out.println("JavaConfService.getCfpUserUid()");
		return "cfp:user:id";
	}

	@Override
	public void sendFeedback(Feedback feedback) {
		System.out.println("JavaConfService.sendFeedback()");
	}

	@Override
	public GluonObservableObject<Location> retrieveLocation() {
		System.out.println("JavaConfService.retrieveLocation()");
		return new GluonObservableObject<>();
	}

	@Override
	public ObservableList<RatingData> retrieveVoteTexts(int rating) {
		System.out.println("JavaConfService.retrieveVoteTexts()");
		return new GluonObservableList<>();
	}
	
}
