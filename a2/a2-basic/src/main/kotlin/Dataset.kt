class Dataset(val datasetName: String, val entries: MutableList<Double>) {
    fun addEntry(entry: Double) {
        entries.add(entry)
    }

    fun modifyEntryAtIndex(index: Int, value: Double) {
        entries[index] = value
    }

    fun removeEntryAtIndex(index: Int) {
        entries.removeAt(index)
    }
}