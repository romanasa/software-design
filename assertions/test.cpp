#include "LRU.h"
#include "gtest/gtest.h"

TEST(simpleTest, test1) {
    LRUCache<int, int> lru(10);
    lru.add(1, 1);
    lru.add(2, 2);
    lru.add(3, 3);
    lru.add(4, 4);
    ASSERT_DEATH(lru.add(1, 2), "");
    ASSERT_EQ(lru.get(1), 1);
    ASSERT_EQ(lru.get(20), std::nullopt);
}

TEST(lowCapacity, test2) {
    LRUCache<int, int> lru(2);
    ASSERT_EQ(lru.get(1), std::nullopt);
    ASSERT_EQ(lru.get(10), std::nullopt);
    lru.add(1, 1);
    lru.add(2, 2);
    ASSERT_EQ(lru.get(1), 1);
    lru.add(3, 3);
    ASSERT_EQ(lru.get(1), 1);
    lru.add(4, 4);
    ASSERT_EQ(lru.get(1), 1);
    ASSERT_EQ(lru.get(4), 4);
    ASSERT_EQ(lru.get(2), std::nullopt);
    ASSERT_EQ(lru.get(3), std::nullopt);
    lru.add(11, 100);
    ASSERT_EQ(lru.get(11), 100);
    ASSERT_EQ(lru.get(1), std::nullopt);
    ASSERT_EQ(lru.get(4), 4);
}

TEST(testString, test1) {
    LRUCache<std::string, std::string> lru(20);
    ASSERT_EQ(lru.get("ghi"), std::nullopt);
    lru.add("abc", "123");
    lru.add("def", "456");
    ASSERT_EQ(lru.get("ghi"), std::nullopt);
    ASSERT_DEATH(lru.add("abc", "123"), "");
    ASSERT_EQ(lru.get("abc"), "123");
    ASSERT_EQ(lru.get("def"), "456");

    lru.remove("def");
    ASSERT_DEATH(lru.remove("def"), "");
    ASSERT_EQ(lru.get("def"), std::nullopt);
    ASSERT_EQ(lru.get("abc"), "123");
}