package net.lyrae;

import java.util.LinkedList;
import java.util.Map;

import net.lyrae.ml.LDA;

public class LDADriver {
	public static void main(String[] args) {
		LinkedList<String> documents = new LinkedList<>();
		documents.add("pizza is delicious and nutritious");
		documents.add("i like pizza it is tasty cheap and good for the body");
		documents.add("eating pizza every day is a good idea");
		documents.add("skiing is very difficullt");
		documents.add("skiing is not the only difficullt winter sport");
		documents.add("another hard winter sport is ice-skateing");
		
		LDA lda = new LDA(documents);
		Map<String, Integer>[] result = lda.getTopics(2, 100);
		
		int topicNum = 1;
		for (Map<String, Integer> topic : result) {
			System.out.print("Topic ");
			System.out.print(topicNum++);
			System.out.println(":");
			
			for (Map.Entry<String, Integer> entry : topic.entrySet()) {
			    System.out.println(entry.getKey() + ", " + entry.getValue());
			}
			System.out.println();
		}
	}
}
