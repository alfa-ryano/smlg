package uk.ac.york.cs.es.smlg.model;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	String id;
	String username;
	String email;
	String password;
	String description;
	Date createTime;
	Date lastUpdate;

	public User() {

	}

	public User(String email, String username, String password) {
		this.id = username;
		this.username = username;
		this.email = email;
		this.password = password;
		this.createTime = new Date();
		this.lastUpdate = this.createTime;
	}

	public static User getUserByUsername(String path, String username) {
		File userDirectory = new File((path + "/user/" + username).replace("/", File.separator));
		File file = new File(userDirectory.getAbsoluteFile() + File.separator + "user.xml");

		User user = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			user = (User) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static boolean userExists(String path, String username) {
		File userDirectory = new File((path + "/user/" + username).replace("/", File.separator));
		return userDirectory.exists();
	}

	public void createDirectory(String path) throws Exception {
		File userDirectory = new File((path + "/user/" + username).replace("/", File.separator));
		if (userDirectory.exists()) {
			throw new Exception("User already existed!");
		}

		if (!userDirectory.exists()) {
			if (!userDirectory.mkdir()) {
				throw new Exception("Cannot create user directory!");
			}
		}

		if (userDirectory.exists()) {
			File file = new File(userDirectory.getAbsoluteFile() + File.separator + "user.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(this, file);
			jaxbMarshaller.marshal(this, System.out);
		}
	}

	@XmlAttribute
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlAttribute
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@XmlAttribute
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
