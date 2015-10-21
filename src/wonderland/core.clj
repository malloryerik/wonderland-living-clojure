
(ns wonderland.core)

;; Notes from Living Clojure, by Carin Meyer

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))




(ns wonderland
  (:require [clojure.set :as s]))

(defn common-fav-foods [foods1 foods2]
  (let [food-set1 (set foods1)
        food-set2 (set foods2)
        common-foods (s/intersection food-set1 food-set2)]
    (str "Common Foods: " common-foods)))

 common-fav-foods(common-fav-foods [:jam :brownies :toast]
                  [:lettuce :carrots :jam])


(defn drinkable? [x]
  (= x :drinkme))
;; Hold on, this just tells us if the arg = :drinkable


(not-any? #(= % :drinkme) [:poison :drinkme])
(every? #(= % :drinkme) [:poison :drinkme])
(some #(= % :drinkme) [:poison :drinkme])

(some #(> % 3) [1 2 3 4 5])

(#{1 2 3 4 5} 3)

(if false
  "it is true"
  "it is false")

(if (= :drinkme :drinkme)
  "Try it"
  "Run away!")

;; p.67 of 325

(let [need-to-grow-small (> 5 3)]
  (if need-to-grow-small
    "drink bottle"
    "don't drink it"))

(if-let [need-to-grow-small (> 5 3)]
  "drink"
  "don't drink")

(defn drinkit [need-to-grow-small]
  (when need-to-grow-small "drink-bottle"))

(drinkit (> 5 2))
(drinkit (> 2 5))

(when-let [need-to-grow-small false]
  "drink bottle")

(let [bottle "mystery"]
  (cond
    (= bottle "poison") "don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"
    :else "unknown"))

;; 'case' a shortcut for cond, if all only one test value and it can be compared with a =

(let [bottle "mystery"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"
    "unknown")) ; this is the last, default case.

(defn grow [name direction]
  (if (= direction :small)
    (str name " is growing smaller")
    (str name " is growing bigger")))

(grow "Alice" :small)


((partial grow "Alice") :big)

(defn toggle-grow [direction]
  (if (= direction :small) :big :small))
