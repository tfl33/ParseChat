# ParseChat
Easy android chat/messages library using Parse

- Send and receive messages between users
- Sends push notifications when users get new messages

## How to use
- Download this repo and add it as a dependency to your app
- Initialize using your parse app credentials:
```java
public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseChat.Builder parseChat = new ParseChat.Builder(this,
                getString(R.string.parseAppId),
                getString(R.string.parseAppKey));
        ParseChat.initialize(parseChat);
    }
}`
```
- Set the parseuser that will be used to send messages
```java
ParseChat.setUser(ParseUser.getCurrentUser());
```
- Show the user's conversations
```java
ParseChat.showConversations(activity);
```
- Create a new conversation - In progress
```java
ParseConversation conversation = new ParseConversation.Builder()
        .setSender(ParseUser.getCurrentUser())
        .setReceiver("recipientUserId")
        .setTitle("Conversation title")
        .build();
ParseChat.startConversation(conversation);
```
## What's next?
- Send images
- Send videos
- Override UI styles
- Add image to conversation list
- Add header view to conversation 
![conversations](https://raw.githubusercontent.com/j-mateo/ParseChat/master/art/parsechat_conversations.png)
![messages](https://raw.githubusercontent.com/j-mateo/ParseChat/master/art/parsechat_messages.png)

