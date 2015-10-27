(ns wonderland.core)

;; Notes from Living Clojure, by Carin Meyer

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))




;; (ns wonderland
;;  )

;; (defn common-fav-foods [foods1 foods2]
;;   (let [food-set1 (set foods1)
;;         food-set2 (set foods2)
;;         common-foods (s/intersection food-set1 food-set2)]
;;     (str "Common Foods: " common-foods)))

 ;; common-fav-foods(common-fav-foods [:jam :brownies :toast]
 ;;                  [:lettuce :carrots :jam])


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

(toggle-grow :big)
;; -> :small

(defn oh-my [direction]
  (str "Oh my! You are growing " direction))

;; combine the toggle-grow and oh-my functions:
(oh-my (toggle-grow :small))

;; use comp to combine them:
(defn surprise [direction]
  ((comp oh-my toggle-grow) direction))

(surprise :big)
;; -> "Oh My! You're growing :small"

;; wrong order - doesn't throw error, but oh-my doesn't work
(defn uhoh [direction]
  ((comp toggle-grow oh-my) direction))

(uhoh :big)
;; -> :small


(defn adder [x y]
  (+ x y))

(adder 3 4)

(def adder5 (partial adder 5))

(adder5 10)
;; -> 15



;; DESTRUCTURING

(let [[color size] ["cobalt" "tiny"]]
  (str "The " color " door is " size "."))
;; -> "The cobalt door is tiny."

;; Without destructuring

(let [x ["blue" "small"]
      color (first x)
      size (last x)]
  (str "The " color " door is " size "."))
;; -> "The blue door is tiny."


;; breaking down a data structure in sequential fashion.
(let [[color [size]] ["blue" ["very small"]]]
  (str "The " color " door is " size "."))


(defn flower-colors [colors]
  (str "The flowers are "
       (:flower1 colors)
       " and "
       (:flower2 colors)))

(flower-colors {:flower1 "red" :flower2 "blue"})
;; => "The flowers are red and blue

(defn dive-bars [bars]
  (str "Have you been to "
       (:dive1 bars) " and " (:dive2 bars) "?"))

(def dives {:dive1 "Seoul Pub" :dive2 "Mike's Cabin" :dive3 "Barfly"})

(dive-bars dives)
;; This last example seems easy to read, and useful.



;; ==================================================
;; LAZINESS
;; ==================================================

;; The book doesn't really explain laziness, except that it has to do with infinity..
;; Laziness basically means, I think, than evaluation isn't done until needed.
;; But, I think Living Clojure is more of a practical guide, and we can look up the formal meaning of laziness later.

(rand-int 10)

(repeat 5 (rand-int 100))
;; -> (26 26 26 26 26)

;; That just repeated the single returned value.
;; Instead we need to repeat a function, to get different values.
#(rand-int 10) ; We'll call this five times.

;; 'Repeatedly' on an anon function will do the trick:
(repeatedly 5 #(rand-int 100))
;; => (25 94 23 55 14)

(take 10 (repeatedly #(rand-int 1000)))
;; be careful not to eval without the "take 10" or.. stack overflow!

(def rabbits ["Jenny" "Ginny" "Johnny"])
(take 5 (cycle rabbits))

(take 3 (rest (cycle rabbits)))
;; => ("Ginny" "Johnny" "Jenny")



;; ==================================================
;; RECURSION p.79 of 325
;; ==================================================


(def alice-is ["normal" "too small" "too big" "swimming"])

(vec (map #(str "Alice is " %) alice-is))

(def animals  ["mouse" "rhino" "fox" "nymph"])
(def colors ["blue" "violet" "golden" "beige"])

(defn gen-animal-string [animal color]
  (str color "-" animal))

(map gen-animal-string animals colors)

(map gen-animal-string animals (cycle ["brown" "black"]))


;; ==================================================
;; REDUCE p.86 of 325
;; ==================================================

(reduce + [1 2 3 4 5 ])

;; See how this is similar to map, but not...
(reduce (fn [r x] (+ r (* x x))) [1 2 3]) ;; btw map wont work here.


(def nilly [:mouse nil :duck nil nil :lory])




;; defin the atom
(def cat-atom (atom :sticky))

;; rese)t the atom
(reset! cat-atom :grey)

@cat-atom

;; not this
;; (complement nil? (nil)) ; can't call nil

;; this
((complement nil?) nil)

;; because it's this:
(nil? nil) ; so you add complexity to the predicate
((complement nil?) nil) ;; like that, and not

(def grey-atom (atom {:needs ["love" "food" "nail clipping" "fur care" "play" "litter change"], :wants ["to go out all the time"]}))

@grey-atom

(def who-atom2 (atom :caterpillar))

@who-atom

;; reset replaces old to new value
(reset! who-atom2 :spacedust)

(defn change [state]
  (case state
    :spacedust :caterpillar
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly :spacedust))



(swap! who-atom2 change)

;; swap! must be free of side effects (so that it's atomic...)


(defn multiplecase [constant]
  (case constant
    (:yo :hi :bye :wow) "yup baby"
      (:in "it" :out) "Where!"
      "default"))

(multiplecase :bye)

;; (def counter (atom 0))

;; @counter ;=> 0

;; (dotimes [_ 5] (swap! counter inc)) ; underscore is name of value, conventional when val not used

;; @counter


;; CONCURRENCY with FUTURE

;; Future exectures the body of a function in another thread

;; (def countit (atom 0))
;; @countit

;; (count "hi")
;;; 
;;; 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; REFS
;; ATOMIC -- Within the transaction, all or noe (if something goes wrong) of the refs in a transaction will be updated.
;; CONSISTENT -- Validator func can check values before transaction commits.
;; ISOLATED -- Each transaction is an island unto itself.

;; Alice in Wonderland ex:
;; Caterpillar says eating bit of righside of mushroom will make her bigger, righthand smaller. Amount of mushroom in each had directly related to how big she is.
;; SO, TWO REFS, one for height in inches, other for number of bites in her right hand.

;; (def alice-height (ref 3))
;; (def right-hand-bites (ref 10))
;; @alice-height
;; ;=> 3
;; @right-hand-bites
;; ;=> 10



;; ;; define a func to inc Alice's height by 24 in for every bit from the right hand.

;; (defn eat-from-right-hand []
;;   (when (pos? @right-hand-bites)
;;     (alter right-hand-bites dec)
;;     (alter alice-height #(+ % 24))))
;; ;; this won't evaluate
;; ;; needs to be in a transaction

;; (dosync (eat-from-right-hand))
;; @alice-height ; 27
;; @right-hand-bites ; 9


;; ;; add dosync into the function
;; (defn eat-from-right-hand []
;;   (dosync  (when (pos? @right-hand-bites)
;;              (alter right-hand-bites dec)
;;              (alter alice-height #(+ % 24)))))


;; (let [n 2]
;;   (future (dotimes [_ n] (eat-from-right-hand)))
;;   (future (dotimes [_ n] (eat-from-right-hand)))
;;   (future (dotimes [_ n] (eat-from-right-hand))))

;; @alice-height
;; @right-hand-bites



;; ref-set
;; (ref-set ref val)
;; sets the value of ref. Must be called in a transaction.

(def fooz (ref {}))

@fooz ;=> {}

(dosync  ; the transaction
 (ref-set fooz {:fooz "bar"}))

@fooz ;=> {:fooz "bar}

;; set refs to initial values
(def x (ref 1))
(def y (ref 1))

(defn new-values []
  (dosync ; refs, so must be in a transaction
   (alter x inc) ; alter on x to change its value with the inc function
   (ref-set y (+ 2 @x))))
                                        ; for y, directly set with ref-set

(let [n 2]
  (future (dotimes [_ 2] (new-values)))
  (future (dotimes [_ 2] (new-values)))
  (future (dotimes [_ 2] (new-values))))

@x
@y

