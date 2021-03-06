package model;

// Generated 2015-1-13 17:33:44 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Message generated by hbm2java
 */
public class Message implements java.io.Serializable {

	private Integer messageId;
	private String fromUser;
	private String toUser;
	private String content;
	private Date sendTime;

	public Message() {
	}

	public Message(String fromUser, String toUser, String content, Date sendTime) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.content = content;
		this.sendTime = sendTime;
	}
	
	private static Queue  messages=new LinkedList();
	public synchronized static Message getMessage(){
		if(messages.isEmpty())
			return null;
		return (Message)(messages.poll());
	}
	public synchronized static void putMessage(Message message){
		messages.add(message);
	}	
	

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getFromUser() {
		return this.fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return this.toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
