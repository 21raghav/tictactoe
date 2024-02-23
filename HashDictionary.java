import java.util.LinkedList;

// Class to implement a dictionary using a hash table
public class HashDictionary implements DictionaryADT {
    // The hash table where each element is a LinkedList to handle collisions
    private LinkedList<Data>[] table;

    // Constructor to initialize the hash table with the specified size
    @SuppressWarnings("unchecked")
    public HashDictionary(int size) {
        // Initialize the table as an array of LinkedLists
        table = (LinkedList<Data>[]) new LinkedList[size];
        for (int i = 0; i < size; i++) {
            // Initialize each LinkedList in the table
            table[i] = new LinkedList<>();
        }
    }

    // Hash function to map a string key to an index in the hash table
    private int hashFunction(String key) {
        // Polynomial hash code calculation for the string key
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = 31 * hash + key.charAt(i); // 31 is used as it is an odd prime
        }
        // Ensure the hash code is within the bounds of the table
        return Math.abs(hash) % table.length;
    }

    // Method to insert a new record into the hash table
    @Override
    public int put(Data record) throws DictionaryException {
        // Find the index for this record using the hash function
        int index = hashFunction(record.getConfiguration());
        // Check for existing records with the same key
        for (Data item : table[index]) {
            if (item.getConfiguration().equals(record.getConfiguration())) {
                // If a record with the same key exists, throw an exception
                throw new DictionaryException();
            }
        }
        // Add the new record at the beginning of the LinkedList at the calculated index
        table[index].addFirst(record);
        // Return 1 if a collision occurred (LinkedList size > 1), otherwise return 0
        return table[index].size() > 1 ? 1 : 0;
    }

    // Method to remove a record from the hash table
    @Override
    public void remove(String config) throws DictionaryException {
        // Find the index for this configuration using the hash function
        int index = hashFunction(config);
        // Attempt to remove the item; if successful, 'removed' will be true
        boolean removed = table[index].removeIf(item -> item.getConfiguration().equals(config));
        if (!removed) {
            // If no item was removed, throw an exception
            throw new DictionaryException();
        }
    }

    // Method to retrieve the score associated with a specific configuration
    @Override
    public int get(String config) {
        // Find the index for this configuration using the hash function
        int index = hashFunction(config);
        for (Data item : table[index]) {
            if (item.getConfiguration().equals(config)) {
                // If the configuration is found, return its score
                return item.getScore();
            }
        }
        // If the configuration is not found, return -1
        return -1;
    }

    // Method to count the total number of records in the hash table
    @Override
    public int numRecords() {
        int count = 0;
        // Iterate through each LinkedList in the table and sum their sizes
        for (LinkedList<Data> list : table) {
            count += list.size();
        }
        // Return the total count of records
        return count;
    }
}
