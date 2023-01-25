#pragma once

#include <unordered_map>
#include <cassert>

template<typename K, typename V>
class LRUCache {
  public:
    explicit LRUCache(size_t capacity = 128);

    std::optional<V> get(const K &key);

    void add(const K &key, const V &value);

    void remove(const K &key);

  private:
    void removeKey(const K &key);

    void pushBack(const K &key, const V &value);

    void assertSizeCorrect();

    void assertKeySynchronized(const K &key);

    void assertKeyExist(const K &key);

    void assertKeyNotExist(const K &key);

    void assertFirstLastSynchronized();

    size_t capacity;
    std::optional<K> head, tail;
    std::unordered_map<K, V> values;
    std::unordered_map<K, std::pair<std::optional<K>, std::optional<K>>> links;
};

template<typename K, typename V>
void LRUCache<K, V>::assertSizeCorrect() {
    assert(links.size() == values.size() && links.size() <= capacity);
    assert(head.has_value() || links.empty());
    assert(tail.has_value() || links.empty());
}

template<typename K, typename V>
void LRUCache<K, V>::assertKeySynchronized(const K &key) {
    assert(links.contains(key) == values.contains(key));
}

template<typename K, typename V>
void LRUCache<K, V>::assertKeyExist(const K &key) {
    assert(links.contains(key) && values.contains(key));
}

template<typename K, typename V>
void LRUCache<K, V>::assertKeyNotExist(const K &key) {
    assert(!links.contains(key) && !values.contains(key));
}

template<typename K, typename V>
void LRUCache<K, V>::assertFirstLastSynchronized() {
    assert(head.has_value() == tail.has_value());
}

template<typename K, typename V>
LRUCache<K, V>::LRUCache(size_t capacity) : capacity(capacity) {
    assert(capacity > 0);
    values.reserve(capacity);
    links.reserve(capacity);

    head = tail = std::nullopt;

    assertSizeCorrect();
}

template<typename K, typename V>
std::optional<V> LRUCache<K, V>::get(const K &key) {
    assertSizeCorrect();
    assertKeySynchronized(key);

    if (!values.contains(key)) {
        return std::nullopt;
    }

    auto tmp = values[key];
    removeKey(key);
    pushBack(key, tmp);
    return tmp;
}

template<typename K, typename V>
void LRUCache<K, V>::add(const K &key, const V &value) {
    assertSizeCorrect();
    assertKeyNotExist(key);
    if (links.size() == capacity) {
        assert(head.has_value());
        auto key_to_remove = head.value();
        remove(key_to_remove);
    }
    pushBack(key, value);
}

template<typename K, typename V>
void LRUCache<K, V>::remove(const K &key) {
    removeKey(key);
    values.erase(key);
}

template<typename K, typename V>
void LRUCache<K, V>::removeKey(const K &key) {
    assertSizeCorrect();
    assertKeyExist(key);

    assert(head.has_value() && tail.has_value());
    auto &[prev, next] = links[key];
    if (head == key) {
        head = next;
    }
    if (tail == key) {
        tail = prev;
    }
    if (next.has_value()) {
        links[*next].first = prev;
    }
    if (prev.has_value()) {
        links[*prev].second = next;
    }
    links.erase(key);
    values.erase(key);

    assertFirstLastSynchronized();

    assertSizeCorrect();
    assertKeyNotExist(key);
}

template<typename K, typename V>
void LRUCache<K, V>::pushBack(const K &key, const V &value) {
    assertSizeCorrect();
    assertKeyNotExist(key);
    assertFirstLastSynchronized();
    if (!tail.has_value()) {
        links[key] = {std::nullopt, std::nullopt};
        head = key;
    } else {
        links[key] = {tail, std::nullopt};
        links[*tail].second = key;
    }
    tail = key;
    values[key] = value;
    assertSizeCorrect();
    assertKeyExist(key);
    assert(tail == key);
}

