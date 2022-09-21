;1-Start CLIPS
;2-Type (load "9311080300.clp")
;3-Type (reset)
;4-Type (run)

(deftemplate Student
	(slot course-code) ; shows if the student compeleted her homework
	(slot study-hour) 
	(slot course-type) ; general or exclusive
	(slot test-no) ; 
	(slot total-mark)
	(slot wrong-answer)
)


(deftemplate Reached-schedule
	(slot reached-schedule)
)
(deftemplate Time-spent
	(slot time-spent)
)

(deftemplate Test-Info
	(slot test-status)
	(slot test-number-limit)
)
(deftemplate Recommendation
	(slot recom)
)
	
;********************************tatbighe informatione gerefte shode az user ba KB***********************************

;******************** Group A rules for daily plan************************
(defrule A1
	(Student (course-code 1))
	=>
	(assert(Reached-schedule (reached-schedule completely)))
	(printout t " you completely reached your daily schedule "crlf)
)

(defrule A2
	(Student (course-code 0))
	=>
	(assert(Reached-schedule (reached-schedule incomplete)))
	(printout t " you didn't reach to do your daily schedule "crlf)
)

(defrule A3
	(Student (course-code ?x))
	(test ( > ?x 1))
	=>
	(assert(Reached-schedule (reached-schedule more-than-what-planned)))
	(printout t "you did more than what was planned for you "crlf)
)

(defrule A4
	(Student (course-code -1))
	=>
	(assert(Reached-schedule (reached-schedule study-different-topic)))
	(printout t " you study different topic "crlf)
)

;***********************Group B rules for study hours*****************************
(defrule B1
	(Student (study-hour 90))
	=>
	(assert (Time-spent(time-spent enough)))
	(printout t "you spent enough time on you studies" crlf)
)

(defrule B2
	(Student (study-hour  ?x))
	(test (< ?x 90))
	=>
	(assert (Time-spent(time-spent not-enough)))
	(printout t "you didnt spend enough time on your studies" crlf)
)

(defrule B3
	(Student (study-hour  ?x))
	(test (> ?x 90))
	=>
	(assert (Time-spent(time-spent more-than-enough)))
	(printout t "you spend more than enough time on your studies!!" crlf)
)

(defrule B4
	(Student (study-hour -1))
	=>
	(assert (Time-spent(time-spent not-insert)))
	(printout t "you spent enough time on you studies" crlf)
)

;**********************Group C rules for test marks**********************************

(defrule c1
	(Student (total-mark ?x))
	(test (>= ?x 90))
	=>
	(assert (Test-Info(test-status excellent)))
	(printout t "your Test Status is Excellent! " crlf)
)


(defrule c2
	(Student (total-mark ?x) (wrong-answer ?y))
	(test (and (< ?x 90) (> ?x 70)))
	(test (<= ?y 5))
	=>
	(assert(Test-Info(test-status good)))
	(printout t "your Test Status is good! " crlf)
)


(defrule c3
	(Student (total-mark ?x) (wrong-answer ?y))
	(test (and (< ?x 90) (> ?x 70)))
	(test (> ?y 5))
	=>
	(assert(Test-Info(test-status normal)))
	(printout t "your Test Status is good! But please put more consentration on your studies " crlf)
)

(defrule c4
	(Student (total-mark ?x))
	(test (and (< ?x 70) (> ?x 50)))
	=>
	(assert(Test-Info(test-status bad)))
	(printout t "your Test Status is bad! put more time and energy on your studies and solve more tests" crlf)
)

(defrule c5
	(Student (total-mark ?x))
	(test (<=  ?x 50))
	=>
	(assert(Test-Info(test-status horrible)))
	(printout t "your Test Status is horrible! you shoud study your daily plan one more time ! and do the test again" crlf)
)


;*****************************Group D rules for test numbers*********************************
; general

(defrule d1
	(Student (course-type general))
	(Student (test-no ?x))
	(test(>= ?x 25))
	=>
	(assert(Test-Info(test-number-limit enough)))
	(printout t "you solved enough test for the course"crlf)
)


(defrule d2
	(Student (course-type general))
	(Student (test-no ?x))
	(test (and (< ?x 25) (>= ?x 20)))
	=>
	(assert(Test-Info(test-number-limit acceptable)))
	(printout t "you solved acceptable amount of tests for the course"crlf)
)

(defrule d3
	(Student (course-type general))
	(Student (test-no ?x))
	(test (and (< ?x 20) (>= ?x 10)))
	=>
	(assert(Test-Info(test-number-limit not-enough)))
	(printout t "you didnt solved enough tests for the course"crlf)
)


(defrule d4
	(Student (course-type general))
	(Student (test-no ?x))
	(test(< ?x 10))
	=>
	(assert(Test-Info(test-number-limit a-few)))
	(printout t "you solved a few tests for the course...its actully nothing! like you didnt solve any! "crlf)
)

;exclusive

(defrule d5
	(Student (course-type exclusive))
	(Student (test-no ?x))
	(test(>= ?x 15))
	=>
	(assert(Test-Info(test-number-limit enough)))
	(printout t "you solved enough test for the course"crlf)
)


(defrule d6
	(Student (course-type exclusive))
	(Student (test-no ?x))
	(test (and (< ?x 15) (>= ?x 10)))
	=>
	(assert(Test-Info(test-number-limit acceptable)))
	(printout t "you solved acceptable amount of tests for the course"crlf)
)

(defrule d7
	(Student (course-type exclusive))
	(Student (test-no ?x))
	(test (and (< ?x 10) (>= ?x 5)))
	=>
	(assert(Test-Info(test-number-limit not-enough)))
	(printout t "you didnt solved enough tests for the course"crlf)
)

(defrule d8
	(Student (course-type exclusive))
	(Student (test-no ?x))
	(test (and (< ?x 5) (> ?x 0)))
	=>
	(assert(Test-Info(test-number-limit a-few)))
	(printout t "you solved a few tests for the course...its actully nothing! like you didnt solve any! "crlf)
)

(defrule d9
	(Student (test-no 0))
	=>
	(assert(Test-Info(test-number-limit nothing)))
	(printout t "you didnt solve any test!!!! go and solve suitable tests for the course and enter the information again"crlf)
)


;************************ Recommendation Rules**************************************

;-------------------------------------------------------------------------
;--------- GeNERAL- hame vizhegiha Ali - bar asase darsade azmoon-----------
;-------------------------------------------------------------------------
(defrule recom1
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status excellent)) ; darsad >=90

	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1:  ginius student" crlf)
)

(defrule recom2

	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status good)); darsad 70<<90- bighalat
	=>
	(assert (Recommendation (recom  2)))
	(printout t crlf "Recommendation 2:  good student" crlf)
)


(defrule recom3

	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status normal)); darsad 70<<90- tedade soal ziad - ghalat >5
	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3:  good student- but bideghat" crlf)
)

(defrule recom4

	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status bad)); darsad 50<<70- ghalat mohem nis
	=>
	(assert (Recommendation (recom  4)))
	(printout t crlf "Recommendation 4:  ba inke kolli dars khooondi- vali BAD dars khoondi- raveshe khoondaneto avaz kon" crlf)
)

(defrule recom5

	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status horrible)); darsad  <=50 - ghalat mohem nis
	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5:  darso khoob nakhoondi 2bare az avval in barname ro ejra kon" crlf)
)

;--------------------------------------------------------------------
;--------- GeNERAL- hame vizhegiha Ali - test kam zade-----------
;--------------------------------------------------------------------
(defrule recom6
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit acceptable)) ; solved test-number 20<<25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  6)))
	(printout t crlf "Recommendation 1:  ginius student" crlf)
)

(defrule recom7
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit not-enough)) ; solved test-number 10<<15
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  7)))
	(printout t crlf "Recommendation 8:  hame chi alie(100 darsade) vali test kam hal karde" crlf)
)

(defrule recom8
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit a-few)) ; solved test-number < 10
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  8)))
	(printout t crlf "Recommendation 8:  ba inke hame chi khoobe test asan nazadi- nemishe tasmim giri kard" crlf)
)
;--------------------------------------------------------------------
;----GeNERAL- hame vizhegiha Ali-zamane dars khoondan kamtar az 90---
;--------------------------------------------------------------------
(defrule recom9
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) ; time-spent on studing each cours < 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1:  ginius student- vaghti ke ezafi avordio bezar roo darsi ke zaeiifi" crlf)
)

;--------------------------------------------------------------------
;----course-code = 1 - time-spent
;--------------------------------------------------------------------
(defrule recom10
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  9)))
	(printout t crlf "Recommendation 9:  " crlf)
)

(defrule recom11
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) ; time-spent on studing each cours = 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  10)))
	(printout t crlf "Recommendation 10: " crlf)
)

(defrule recom12
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) ; time-spent on studing each cours < 90 min
	(Test-Info(test-number-limit enough)) ; solved test-number > 25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1: " crlf)
)

;--------------------------------------------------------------------
;----GeNERAL-- course-code = 1 - study-hour = 95, testnum 26- darsad???
;--------------------------------------------------------------------
(defrule recom13
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit acceptable)) ; solved test-number 20-25
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)

(defrule recom14
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit not-enough)) ; solved test-number 10-20
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  12)))
	(printout t crlf "Recommendation 12: " crlf)
	
)

(defrule recom15
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit a-few)) ; solved test-number 10-20
	(Test-Info(test-status excellent)) ; darsad >=90
	=>
	(assert (Recommendation (recom  12)))
	(printout t crlf "Recommendation 12: " crlf)
	
)

(defrule recom16
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) ; time-spent on studing each cours > 90 min
	(Test-Info(test-number-limit nothing)) ; solved test-number 0
	;(Test-Info(test-status excellent)) ; darsad >=90  vaghti hichi test hal nakarde bi manie
	=>
	(assert (Recommendation (recom  13)))
	(printout t crlf "Recommendation 13: " crlf)
	
)

;************************ getting information from the user*************************


;--------------------------------------------------------------------
;---- course-code = 1 - study-hour = 95, darsad moteghayer
;--------------------------------------------------------------------

(defrule recom17
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)
(defrule recom18
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  14)))
	(printout t crlf "recommendation 14: " crlf)
	
)
(defrule recom19
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  15)))
	(printout t crlf "Recommendation 15: " crlf)
	
)

(defrule recom20
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Student (course-type exclusive))
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  16)))
	(printout t crlf "Recommendation 16: " crlf)
)


(defrule recom21
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Student (course-type general))
	(Test-Info(test-status horrible)) 

	=>
	(assert (Recommendation (recom  15)))
	(printout t crlf "Recommendation 15: " crlf)
)

;--------------------------------------------------------------------
;---- course-code = 1 - study-hour = 95,  - darsad moteghayer
;--------------------------------------------------------------------

(defrule recom22
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  17)))
	(printout t crlf "Recommendation 17: " crlf)
)
(defrule recom23
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  17)))
	(printout t crlf "Recommendation 17: " crlf)
	
)
(defrule recom24
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  14)))
	(printout t crlf "Recommendation 14: " crlf)
)

(defrule recom25
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  15)))
	(printout t crlf "Recommendation 15: " crlf)
)
;--------------------------------------------------------------------
 ;course-code = 1 - study-hour = 95,   test-statude = not enough- darsad moteghayer
;--------------------------------------------------------------------

(defrule recom26
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  18)))
	(printout t crlf "Recommendation 18: " crlf)
)

(defrule recom27
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  18)))
	(printout t crlf "Recommendation 18: " crlf)
	
)
(defrule recom28
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  19)))
	(printout t crlf "Recommendation 19: " crlf)
	
)
(defrule recom29
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  19)))
	(printout t crlf "Recommendation 19: " crlf)
)
;--------------------------------------------------------------------
 ;course-code = 1 - study-hour = 95,  test-statude = a-few- darsad moteghayer
;--------------------------------------------------------------------

(defrule recom30
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  18)))
	(printout t crlf "Recommendation 18: " crlf)
)

(defrule recom34
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  18)))
	(printout t crlf "Recommendation 18: " crlf)
	
)
(defrule recom35
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  19)))
	(printout t crlf "Recommendation 19: " crlf)
	
)
(defrule recom36
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  19)))
	(printout t crlf "Recommendation 19: " crlf)
)

;--------------------------------------------------------------------
; course-code = 1 - study-hour = 95,  test-statude = nothing- darsad moteghayer
;--------------------------------------------------------------------

(defrule recom37
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  13)))
	(printout t crlf "Recommendation 13: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour = 90,  test-statude = excellent... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom38
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status excellent)) 

	=>
	(assert (Recommendation (recom  20)))
	(printout t crlf "Recommendation 20: " crlf)
)

(defrule recom39
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  21)))
	(printout t crlf "Recommendation 21: " crlf)
)

(defrule recom40
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  21)))
	(printout t crlf "Recommendation 21: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour = 90,  test-status = nothing- darsad moteghayer
;--------------------------------------------------------------------
(defrule recom41
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  13)))
	(printout t crlf "Recommendation 13: " crlf)
)

;--------------------------------------------------------------------
; course-code = 1 - study-hour = 90,  test-statude = good... test-number-limit = ?
;--------------------------------------------------------------------

(defrule recom120
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  22)))
	(printout t crlf "Recommendation 22 My added: " crlf)
)

(defrule recom42
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  22)))
	(printout t crlf "Recommendation 22: " crlf)
)

(defrule recom43
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  23)))
	(printout t crlf "Recommendation 23: " crlf)
	
)
(defrule recom44
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  23)))
	(printout t crlf "Recommendation 23: " crlf)
	
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour = 90,  test-statude = normal... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom45
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  22)))
	(printout t crlf "Recommendation 22: " crlf)
)

(defrule recom46
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  23)))
	(printout t crlf "Recommendation 23: " crlf)
)
(defrule recom47
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  23)))
	(printout t crlf "Recommendation 23: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour = 90,  test-statude = bad... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom48
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  19)))
	(printout t crlf "Recommendation 19: " crlf)
)

(defrule recom49
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  24)))
	(printout t crlf "Recommendation 24: " crlf)
)
(defrule recom50
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  24)))
	(printout t crlf "Recommendation 24: " crlf)
)

(defrule recom51
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status horrible)) 

	=>
	(assert (Recommendation (recom  15)))
	(printout t crlf "Recommendation 15: " crlf)
)
(defrule recom52
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  24)))
	(printout t crlf "Recommendation 24: " crlf)
)

(defrule recom53
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  25)))
	(printout t crlf "Recommendation 25: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour < 90,  test-statude = excellent... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom54
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1: " crlf)
)
(defrule recom55
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  26)))
	(printout t crlf "Recommendation 26: " crlf)
)
(defrule recom56
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  26)))
	(printout t crlf "Recommendation 26: " crlf)
)
(defrule recom57
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit nothing)) 

	=>
	(assert (Recommendation (recom  27)))
	(printout t crlf "Recommendation 27: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour < 90,  test-statude = good... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom58
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  28)))
	(printout t crlf "Recommendation 28: " crlf)
)

(defrule recom59
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  29)))
	(printout t crlf "Recommendation 29: " crlf)
)
(defrule recom60
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  29)))
	(printout t crlf "Recommendation 29: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour < 90,  test-statude = normal... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom61
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  30)))
	(printout t crlf "Recommendation 30: " crlf)
)
(defrule recom62
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  29)))
	(printout t crlf "Recommendation 29: " crlf)
)

(defrule recom62
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  29)))
	(printout t crlf "Recommendation 29: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour < 90,  test-statude = bad... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom63
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)
(defrule recom64
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status bad)) 
	(Student (course-type exclusive))
	=>
	(assert (Recommendation (recom  32)))
	(printout t crlf "Recommendation 32: " crlf)
)
(defrule recom65
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status bad)) 
	(Student (course-type exclusive))

	=>
	(assert (Recommendation (recom  32)))
	(printout t crlf "Recommendation 32: " crlf)
)


(defrule recom66
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status bad)) 
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)
(defrule recom67
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status bad)) 
	(Student (course-type general))

	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)
;--------------------------------------------------------------------
; course-code = 1 - study-hour < 90,  test-statude = horrible... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom68
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-status horrible)) 
	(Student (course-type exclusive))

	=>
	(assert (Recommendation (recom  33)))
	(printout t crlf "Recommendation 33: " crlf)
)

(defrule recom69
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-status horrible)) 
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)

;--------------------------------------------------------------------
; course-code = 2 - study-hour > 90,  test-statude = good... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom70
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3: " crlf)
)
(defrule recom71
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  34)))
	(printout t crlf "Recommendation 34: " crlf)
)
(defrule recom72
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  35)))
	(printout t crlf "Recommendation 35: " crlf)
)
(defrule recom73
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit nothing)) 

	=>
	(assert (Recommendation (recom  35)))
	(printout t crlf "Recommendation 35: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour > 90,  test-statude = normal... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom74
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  34)))
	(printout t crlf "Recommendation 34: " crlf)
)
(defrule recom75
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  34)))
	(printout t crlf "Recommendation 34: " crlf)
)
(defrule recom76
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour > 90,  test-statude = bad... test-number-limit = ?
;--------------------------------------------------------------------

(defrule recom77
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)

;--------------------------------------------------------------------
; course-code = 2 - study-hour > 90,  test-statude = horrible... test-number-limit = ?
;--------------------------------------------------------------------

(defrule recom78
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent more-than-enough)) 
	(Test-Info(test-status horrible)) 

	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)

;--------------------------------------------------------------------
; course-code = 2 - study-hour = 90,  test-statude = excellent... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom79
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status excellent)) 

	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1: " crlf)
)
(defrule recom80
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1: " crlf)
)
(defrule recom81
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status excellent)) 

	=>
	(assert (Recommendation (recom  6)))
	(printout t crlf "Recommendation 6: " crlf)
)
(defrule recom82
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  8)))
	(printout t crlf "Recommendation 8: " crlf)
)
(defrule recom83
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  36)))
	(printout t crlf "Recommendation 36: " crlf)
)

;--------------------------------------------------------------------
; course-code = 2 - study-hour = 90,  test-statude = good... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom84
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3: " crlf)
)
(defrule recom85
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3: " crlf)
)
(defrule recom86
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  37)))
	(printout t crlf "Recommendation 37: " crlf)
)
(defrule recom87
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 
	=>
	(assert (Recommendation (recom  37)))
	(printout t crlf "Recommendation 37: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour = 90,  test-statude = normal... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom88
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  4)))
	(printout t crlf "Recommendation 4: " crlf)
)

(defrule recom89
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 
	=>
	(assert (Recommendation (recom  4)))
	(printout t crlf "Recommendation 4: " crlf)
)
(defrule recom90
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  105)))
	(printout t crlf "Recommendation 105: " crlf)
)
(defrule recom91
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  106)))
	(printout t crlf "Recommendation 106: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour = 90,  test-statude = bad... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom92
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  107)))
	(printout t crlf "Recommendation 107: " crlf)
)
(defrule recom93
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  37)))
	(printout t crlf "Recommendation 37: " crlf)
)
(defrule recom94
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  38)))
	(printout t crlf "Recommendation 39: " crlf)
)
(defrule recom95
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status bad)) 

	=>
	(assert (Recommendation (recom  38)))
	(printout t crlf "Recommendation 38: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour = 90,  test-statude = horrible... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom96
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status horrible)) 

	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour < 90,  test-statude = excellent... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom97
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status excellent)) 

	=>
	(assert (Recommendation (recom  1)))
	(printout t crlf "Recommendation 1: " crlf)

)
(defrule recom98
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status excellent)) 

	=>
	(assert (Recommendation (recom  20)))
	(printout t crlf "Recommendation 20: " crlf)
)

(defrule recom99
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status excellent)) 
	=>
	(assert (Recommendation (recom  39)))
	(printout t crlf "Recommendation 39: " crlf)
)

;--------------------------------------------------------------------
; course-code = 2 - study-hour < 90,  test-statude = good... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom100
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  21)))
	(printout t crlf "Recommendation 21: " crlf)

)
(defrule recom101
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  40)))
	(printout t crlf "Recommendation 40: " crlf)
)
(defrule recom102
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  40)))
	(printout t crlf "Recommendation 40: " crlf)
)
(defrule recom103
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status good)) 

	=>
	(assert (Recommendation (recom  40)))
	(printout t crlf "Recommendation 40: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour < 90,  test-statude = normal... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom104
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  30)))
	(printout t crlf "Recommendation 30: " crlf)
)
(defrule recom105
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit acceptable)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  30)))
	(printout t crlf "Recommendation 30: " crlf)
)

(defrule recom106
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit not-enough)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  36)))
	(printout t crlf "Recommendation 36: " crlf)
)
(defrule recom107
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-number-limit a-few)) 
	(Test-Info(test-status normal)) 

	=>
	(assert (Recommendation (recom  36)))
	(printout t crlf "Recommendation 36: " crlf)
)
;--------------------------------------------------------------------
; course-code = 2 - study-hour < 90,  test-statude = bad... test-number-limit = ?
;--------------------------------------------------------------------
(defrule recom108
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-status bad)) 
	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)
(defrule recom109
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-enough)) 
	(Test-Info(test-status horrible)) 
	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)

;---------------------------------------------------------------------
;----- study-completely different-------------------------------------
;---------------------------------------------------------------------
(defrule recom121
	(declare (salience -1))
	(Reached-schedule (reached-schedule study-different-topic))
	=>
	(assert (Recommendation (recom  41)))
	(printout t crlf "Recommendation 41: " crlf)
)
;--------------------------------------------------------------------------
;------study-hour is -1 not inserted---------------------------------------
;--------------------------------------------------------------------------
(defrule recom122
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom123
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom124
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)

(defrule recom125
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  43)))
	(printout t crlf "Recommendation 43: " crlf)
)

(defrule recom126
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  44)))
	(printout t crlf "Recommendation 44: " crlf)
)
;-------------------------------------------------------------------------------------------

(defrule recom127
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom128
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom129
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)

(defrule recom130
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  43)))
	(printout t crlf "Recommendation 43: " crlf)
)

;-------------------------------------------------------------------------------------


(defrule recom131
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3: " crlf)
)

(defrule recom132
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  3)))
	(printout t crlf "Recommendation 3: " crlf)
)

(defrule recom133
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  45)))
	(printout t crlf "Recommendation 45: " crlf)
)

(defrule recom134
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  8)))
	(printout t crlf "Recommendation 8: " crlf)
)

;--------------------------------------------------------------------------------------------

(defrule recom135
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  45)))
	(printout t crlf "Recommendation 45: " crlf)
)

(defrule recom136
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  45)))
	(printout t crlf "Recommendation 45: " crlf)
)

(defrule recom137
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  24)))
	(printout t crlf "Recommendation 24: " crlf)
)

(defrule recom138
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  24)))
	(printout t crlf "Recommendation 24: " crlf)
)
;---------------------------------------------------------------------------------

(defrule recom139
	(declare (salience -1))
	(Reached-schedule (reached-schedule more-than-what-planned))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status horrible))
	=>
	(assert (Recommendation (recom  5)))
	(printout t crlf "Recommendation 5: " crlf)
)

;---------------------------completely--------------------------------------------------

(defrule recom140
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom141
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom142
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)

(defrule recom143
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  43)))
	(printout t crlf "Recommendation 43: " crlf)
)

(defrule recom144
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  46)))
	(printout t crlf "Recommendation 46: " crlf)
)
;-------------------------------------------------------------------------------------------

(defrule recom145
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom146
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  42)))
	(printout t crlf "Recommendation 42: " crlf)
)

(defrule recom147
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  11)))
	(printout t crlf "Recommendation 11: " crlf)
)

(defrule recom148
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status good))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  43)))
	(printout t crlf "Recommendation 43: " crlf)
)

;-------------------------------------------------------------------------------------


(defrule recom149
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  47)))
	(printout t crlf "Recommendation 47: " crlf)
)

(defrule recom150
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  47)))
	(printout t crlf "Recommendation 47: " crlf)
)

(defrule recom151
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  48)))
	(printout t crlf "Recommendation 48: " crlf)
)

(defrule recom152
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  48)))
	(printout t crlf "Recommendation 48: " crlf)
)

;--------------------------------------------------------------------------------------------

(defrule recom153
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Student (course-type exclusive))
	=>
	(assert (Recommendation (recom  33)))
	(printout t crlf "Recommendation 33: " crlf)
)

(defrule recom154
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status bad))
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)

;---------------------------------------------------------------------------------

(defrule recom153
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status horrible))
	(Student (course-type exclusive))
	=>
	(assert (Recommendation (recom  33)))
	(printout t crlf "Recommendation 33: " crlf)
)

(defrule recom154
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Time-spent(time-spent not-insert)) 
	(Test-Info(test-status horrible))
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)
;---------------------------------incomplete----------------------------------------------
(defrule recom155
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  49)))
	(printout t crlf "Recommendation 49: " crlf)
)

(defrule recom156
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status excellent))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  49)))
	(printout t crlf "Recommendation 49: " crlf)
)

(defrule recom157
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  50)))
	(printout t crlf "Recommendation 50: " crlf)
)

(defrule recom158
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  50)))
	(printout t crlf "Recommendation 50: " crlf)
)

(defrule recom159
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-number-limit nothing)) 
	=>
	(assert (Recommendation (recom  51)))
	(printout t crlf "Recommendation 51: " crlf)
)
;-------------------------------------------------------------------------------------------

(defrule recom160
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status good))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  49)))
	(printout t crlf "Recommendation 49: " crlf)
)

(defrule recom161
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status good))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  49)))
	(printout t crlf "Recommendation 49: " crlf)
)

;-------------------------------------------------------------------------------------


(defrule recom162
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit enough)) 
	=>
	(assert (Recommendation (recom  52)))
	(printout t crlf "Recommendation 52: " crlf)
)

(defrule recom163
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit acceptable)) 
	=>
	(assert (Recommendation (recom  52)))
	(printout t crlf "Recommendation 52: " crlf)
)

(defrule recom164
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit not-enough)) 
	=>
	(assert (Recommendation (recom  48)))
	(printout t crlf "Recommendation 48: " crlf)
)

(defrule recom165
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status normal))
	(Test-Info(test-number-limit a-few)) 
	=>
	(assert (Recommendation (recom  48)))
	(printout t crlf "Recommendation 48: " crlf)
)

;--------------------------------------------------------------------------------------------

(defrule recom166
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status bad))
	(Student (course-type exclusive))
	=>
	(assert (Recommendation (recom  33)))
	(printout t crlf "Recommendation 33: " crlf)
)

(defrule recom167
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status bad))
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)

;---------------------------------------------------------------------------------

(defrule recom169
	(declare (salience -1))
	(Reached-schedule (reached-schedule incomplete))
	(Test-Info(test-status horrible))
	(Student (course-type exclusive))
	=>
	(assert (Recommendation (recom  33)))
	(printout t crlf "Recommendation 33: " crlf)
)

(defrule recom170
	(declare (salience -1))
	(Reached-schedule (reached-schedule completely))
	(Test-Info(test-status horrible))
	(Student (course-type general))
	=>
	(assert (Recommendation (recom  31)))
	(printout t crlf "Recommendation 31: " crlf)
)
