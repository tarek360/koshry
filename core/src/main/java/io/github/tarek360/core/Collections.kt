package io.github.tarek360.core


inline fun <T, R> Iterable<T>.filterThenMapIfNotNull(transform: (T) -> R?): List<R> {
    val size = if (this is Collection<*>) this.size else 10
    val list = ArrayList<R>(size)
    for (element in this) {
        val mapped = transform(element)
        if (mapped != null) {
            list.add(mapped)
        }
    }
    return list
}

inline fun <T, R> Iterable<T>.filterThenMap(predicate: (T) -> Boolean, transform: (T) -> R): List<R> {
    val size = if (this is Collection<*>) this.size else 10
    val list = ArrayList<R>(size)
    for (element in this) {
        if (predicate(element)) {
            val mapped = transform(element)
            list.add(mapped)
        }
    }
    return list
}

