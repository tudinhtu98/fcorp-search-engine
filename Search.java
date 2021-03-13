import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Search {
    private String[] docs;
    private Map<String, Set<Integer>> terms = new HashMap<String, Set<Integer>>();

    public Search(String[] docs) { // constructor
        this.docs = docs;
        // Inverted index
        for (int i = 0; i < docs.length; i++) {
            String[] words = docs[i].split("\\s+");
            for (String word : words) {
                word = Search.normalize(word);
                if (!terms.containsKey(word)) {
                    terms.put(word, new HashSet<Integer>());
                    Set<Integer> set = terms.get(word);
                    set.add(i);
                } else {
                    Set<Integer> set = terms.get(word);
                    set.add(i);
                }
            }
        }
    }

    public static String normalize(String word) {
        return word.toLowerCase().replaceAll("[^\\w]", "");
        /*
        This method using to normalize string
        */
    }

    public int[] findDocs(String keyword) { // method that returns array/list document id
        if (terms.containsKey(keyword)) {
            Set<Integer> set = terms.get(keyword);

            //Convert Set to Array
            int[] array = new int[set.size()];
            int k = 0;
            for (int i : set)
                array[k++] = i;
            return array;
        }
        return new int[] {};
    }

    public String getDocByIndex(int index) {
        return this.docs[index];
    }

    public static void main(String args[]) {
        Search search = new Search(new String[] {
                "Bank regulation: Bank regulation is a form of government regulation which subjects banks to certain requirements",
                "Banking crisis: When banks suffer a sudden rush of withdrawals by depositors, this is called a bank run" });
        int[] results = search.findDocs(Search.normalize("bank"));

        System.out.print("There are " + results.length + " results at index: ");
        for (int result : results) {
            System.out.print(result + " ");
        }
    }
}