package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Topic;
import repositories.TopicRepository;

@Service
@Transactional
public class TopicService {

	// Managed repository

	@Autowired
	private TopicRepository topicRepository;

	// Supported services

	public TopicService() {
		super();
	}

	public Topic create() {
		return new Topic();
	}

	public Collection<Topic> findAll() {
		return this.topicRepository.findAll();
	}

	public Topic findOne(final int topicId) {
		return this.topicRepository.findOne(topicId);

	}

	public void save(final Topic topic) {
		this.topicRepository.save(topic);
	}

	public void delete(final Topic topic) {
		this.topicRepository.delete(topic);
	}
}
