package com.devoxx.views;

import com.devoxx.DevoxxApplication;
import com.devoxx.DevoxxView;
import com.devoxx.model.Session;
import com.devoxx.model.Vote;
import com.devoxx.service.Service;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Rating;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class VotePresenter extends GluonPresenter<DevoxxApplication> {

    private  static final int MINIMUM_CHARACTERS = 20;

    @FXML private ResourceBundle bundle = ResourceBundle.getBundle("com/devoxx/views/vote");
    @FXML private View vote;
    @FXML private Label title;
    @FXML private Label ratingLabel;
    @FXML private Rating rating;
    @FXML private Label compliment;
    @FXML private ListView<Comment> comments;
    @FXML private TextField feedback;

    @Inject private Service service;

    private ObservableList<Comment> compliments = FXCollections.observableArrayList(GoodComment.values());
    private ObservableList<Comment> improvements = FXCollections.observableArrayList(ImprovementComment.values());

    private Session session;

    public void initialize() {

        vote.setOnShowing(event -> {
            AppBar appBar = getApp().getAppBar();
            appBar.setTitleText(DevoxxView.VOTE.getTitle());
            appBar.setNavIcon(MaterialDesignIcon.CLEAR.button(e -> {
                MobileApplication.getInstance().switchToPreviousView().ifPresent(view -> {
                    DevoxxView.getAppView(view).ifPresent(av -> {
                        av.getPresenter().ifPresent(presenter -> {
                            ((SessionPresenter)presenter).showSession(session, SessionPresenter.Pane.INFO);
                        });
                    });
                });
            }));
        });

        vote.setOnShown(e -> {
            // Make sure that TextField doesn't have focus
            // and is able to show the prompt text
            rating.requestFocus();
        });

        comments.setItems(compliments);
        rating.ratingProperty().addListener((o, ov, nv) -> {
            double rating = nv.doubleValue();
            if (rating == 5) {
                ratingLabel.setText(bundle.getString("OTN.VOTE.EXCELLENT"));
                compliment.setText(bundle.getString("OTN.VOTE.COMPLIMENT"));
                comments.setItems(compliments);
                comments.scrollTo(0);
            } else if (rating >= 3) {
                ratingLabel.setText(bundle.getString("OTN.VOTE.GOOD"));
                compliment.setText(bundle.getString("OTN.VOTE.COMPLIMENT"));
                comments.setItems(compliments);
                comments.scrollTo(0);
            } else {
                ratingLabel.setText(bundle.getString("OTN.VOTE.POOR"));
                compliment.setText(bundle.getString("OTN.VOTE.IMPROVEMENT"));
                comments.setItems(improvements);
                comments.scrollTo(0);
            }
        });

        comments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        comments.setCellFactory(param -> new UnselectListCell<Comment>() {

            @Override
            protected void updateItem(Comment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(item.getComment());
                    imageView.setImage(item.getImage());
                    setGraphic(imageView);
                }
            }
        });

        feedback.setErrorValidator(s -> {
            if(s.length() > 0 && s.length() < MINIMUM_CHARACTERS) {
                return 20 - s.length() + " more character(s)..";
            } else {
                return "";
            }
        });
    }

    public void showVote(Session session) {
        this.session = session;
        if (session != null) {
            title.setText(session.getTitle());
        }
    }

    @FXML
    private void submit() {

        int length = feedback.getText().length();
        if (length > 0 && length < MINIMUM_CHARACTERS) {
            feedback.requestFocus();
        } else {
            // Submit Vote to Backend
            service.voteTalk(createVote(session.getTalk().getId()));
            // Switch to INFO Pane
            DevoxxView.SESSION.switchView().ifPresent(presenter -> {
                SessionPresenter sessionPresenter = (SessionPresenter) presenter;
                sessionPresenter.showSession(session, SessionPresenter.Pane.INFO);
            });
            // Show Toast
            Toast toast = new Toast(bundle.getString("OTN.VOTEPANE.SUBMIT_VOTE"));
            toast.show();
        }
    }

    private Vote createVote(String talkId) {
        Vote vote = new Vote(talkId);
        // TODO: Replace with predefined compliment/improvement selected
        /*vote.setDelivery(delivery.getText());
        vote.setContent(content.getText());*/
        vote.setOther(feedback.getText());
        vote.setValue((int) rating.getRating());
        return vote;
    }


    private class UnselectListCell<T> extends ListCell<T> {

        protected final ImageView imageView;
        private boolean isDrag;

        public UnselectListCell() {
            imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            Platform.runLater(() -> prefWidthProperty().bind(getListView().widthProperty().divide(3.2)));
            addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
                isDrag = false;
            });

            addEventFilter(MouseEvent.DRAG_DETECTED, event -> {
                isDrag = true;
            });

            addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                if (!isDrag) {
                    if (!isEmpty()) {
                        MultipleSelectionModel<T> selectionModel = getListView().getSelectionModel();
                        int index = getIndex();
                        if (selectionModel.getSelectedIndex() == index) {
                            selectionModel.clearSelection(index);
                        } else {
                            selectionModel.select(index);
                        }
                    }
                    event.consume();
                }
            });
        }
    }

    public interface Comment {
        String getComment();
        Image getImage();
    }

    public enum GoodComment implements Comment  {
        FUN("FUN!", new Image(VotePresenter.class.getResource("/star/star-1.png").toExternalForm())),
        DEMOS("I loved the demos", new Image(VotePresenter.class.getResource("/star/star-2.png").toExternalForm())),
        LEARNED_SOMETHING("Learned something new", new Image(VotePresenter.class.getResource("/star/star-3.png").toExternalForm())),
        INTERESTING("Very interesting", new Image(VotePresenter.class.getResource("/star/star-4.png").toExternalForm()));

        private final String comment;
        private final Image image;

        GoodComment(String comment, Image image) {
            this.comment = comment;
            this.image = image;
        }

        @Override
        public String getComment() {
            return comment;
        }

        @Override
        public Image getImage() {
            return image;
        }
    }

    public enum ImprovementComment implements Comment {
        HARD("Hard to understand", new Image(VotePresenter.class.getResource("/star/star-1.png").toExternalForm())),
        FAST("Too fast", new Image(VotePresenter.class.getResource("/star/star-2.png").toExternalForm())),
        DEMOS("Not enough demos/samples", new Image(VotePresenter.class.getResource("/star/star-3.png").toExternalForm())),
        COMPLICATED("Complicated", new Image(VotePresenter.class.getResource("/star/star-4.png").toExternalForm()));

        private final String comment;
        private final Image image;

        ImprovementComment(String comment, Image image) {
            this.comment = comment;
            this.image = image;
        }

        @Override
        public String getComment() {
            return comment;
        }

        @Override
        public Image getImage() {
            return image;
        }
    }
}
