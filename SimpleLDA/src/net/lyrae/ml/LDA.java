package net.lyrae.ml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.lyrae.math.MatrixInt;

public class LDA {
	private LinkedList<WeightedDocument> weightedDocuments;
	private HashMap<String,Integer> dictionary;
	private MatrixInt theta;
	private MatrixInt phi;
	private int wordCount;
	
	private double[] beta;
	private double[] alpha;
	double betaSum;
	double alphaSum;
	
	private Random rand = new Random();
	
	public LDA(List<String> documents) {
		weightedDocuments = new LinkedList<>();
		dictionary = new HashMap<>();
		wordCount = 0;
		
		for (String document : documents) {
			String[] words = document.split(" ");
			weightedDocuments.add(new WeightedDocument(words, new int[words.length]));
			
			for (String word : words)			
				if (dictionary.get(word) == null)
					dictionary.put(word, wordCount++);
		}
	}
	
	public Map<String, Integer>[] getTopics(int numTopics, int loops) {
		return getTopics(numTopics, 50/numTopics, 0.1, loops);
	} 
	
	public Map<String, Integer>[] getTopics(int numTopics, double alphaVal, double betaVal, int loops) {	
		double[] beta = new double[numTopics];
		double[] alpha = new double[numTopics];
		
		for (int i = 0; i < numTopics; ++i) {
			beta[i] = betaVal;
			alpha[i] = alphaVal;
		}
		
		return getTopics(numTopics, alpha, beta, loops);
	}
	
	public Map<String, Integer>[] getTopics(int numTopics, double[] alpha, double[] beta, int loops) {
		this.beta = beta;
		this.alpha = alpha;
		betaSum = 0;
		alphaSum = 0;
		for (int i = 0; i < numTopics; ++i) {
			betaSum += beta[i];
			alphaSum += alpha[i];
		}
		
		theta = new MatrixInt(weightedDocuments.size(), numTopics);
		phi = new MatrixInt(numTopics, dictionary.size());
		
		
		/* Set initial weights */
		int documentCnt = 0;
		for (WeightedDocument ws : weightedDocuments) {
			for (int i = 0; i < ws.length(); ++i) {
				int topic = ws.weights[i] = rand.nextInt(numTopics);
				theta.set(documentCnt, topic, theta.get(documentCnt, topic) + 1);
				
				int wordIndex = dictionary.get(ws.strings[i]);
				phi.set(topic, wordIndex, phi.get(topic, wordIndex)+1);
			}
			
			++documentCnt;
		}
		
		/* Loop until satisfactory converge is reached */
		while (loops > 0) {
			documentCnt = 0;
			
			for (WeightedDocument ws : weightedDocuments) {
				for (int i = 0; i < ws.length(); ++i) {
					int newTopic = -1;
					double newTopicScore = -1;
					
					int thetaSum = theta.rowSum(documentCnt);
					
					int wordIndex = dictionary.get(ws.strings[i]);
					
					for (int topic = 0; topic < numTopics; ++topic) {
						double score;
						
						int phiSum = phi.rowSum(topic);
						
						score = ((theta.get(documentCnt, topic) + alpha[topic]) / (thetaSum+alphaSum))
									*((phi.get(topic, wordIndex) + beta[topic]) / (phiSum+betaSum));
						
						if (score > newTopicScore) {
							newTopic = topic;
							newTopicScore = score;
						}
					}
					int oldTopic = ws.weights[i];
					if (oldTopic != newTopic) {
						ws.weights[i] = newTopic;
						
						theta.set(documentCnt, oldTopic, theta.get(documentCnt, oldTopic) - 1);
						theta.set(documentCnt, newTopic, theta.get(documentCnt, newTopic) + 1);
						
						phi.set(oldTopic, wordIndex, phi.get(oldTopic, wordIndex)+1);
						phi.set(newTopic, wordIndex, phi.get(newTopic, wordIndex)+1);
					}
				}
				++documentCnt;
			}
			--loops;
		}
		
		/* Sort and count words in each topic */
		Map<String, Integer>[] result = (Map<String, Integer>[]) new Map[numTopics];
		
		for (int i = 0; i < numTopics; ++i)
			result[i] = new HashMap<String, Integer>();
		
		for (WeightedDocument wd : weightedDocuments) {
			for (int i = 0; i < wd.length(); ++i) {
				Map<String, Integer> topic = result[wd.weights[i]];
				String word = wd.strings[i];
				
				Integer numOccurrence = topic.get(word);
				if (numOccurrence == null)
					topic.put(word, 1);
				else
					topic.put(word, numOccurrence+1);
			}
		}
		
		return result;
	} 
	
	private class WeightedDocument {
		public String[] strings;
		public int[] weights;
		WeightedDocument(String[] strings, int[] weights) {
			this.strings = strings;
			this.weights = weights;
		}
		public int length() {
			return strings.length;
		}
	}
}
