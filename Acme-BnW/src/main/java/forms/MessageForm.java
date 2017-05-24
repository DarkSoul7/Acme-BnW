
package forms;

public class MessageForm {

	private int		id;
	private String	title;
	private String	description;
	private int		topicId;


	//Constructor
	public MessageForm() {
		super();
	}

	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public int getTopicId() {
		return this.topicId;
	}
	public void setTopicId(final int topicId) {
		this.topicId = topicId;
	}

}
