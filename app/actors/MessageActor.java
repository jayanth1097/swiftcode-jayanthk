package actors;

import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

import static data.Message.Sender.BOT;
import static data.Message.Sender.USER;

public class MessageActor extends UntypedActor {

    //object of feed service
    //object of newsagentservice

    private final ActorRef out;
    private FeedService feedService = new FeedService();
    private NewsAgentService newsAgentService = new NewsAgentService();
    private NewsAgentResponse newsAgentResponse;


    public MessageActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }


    @Override
    public void onReceive(Object message) throws Throwable {
        //send back the response
        ObjectMapper objectMapper = new ObjectMapper();
        if (message instanceof String) {
            Message messageObject = new Message();
            messageObject.text = message.toString();
            messageObject.sender = USER; //messageObject.sender.USER
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            String query = newsAgentService.getNewsAgentResponse("Find " + message, UUID.randomUUID()).query;
            FeedResponse feedResponse = feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse = feedResponse;
            messageObject.sender = BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());

        }

    }
}