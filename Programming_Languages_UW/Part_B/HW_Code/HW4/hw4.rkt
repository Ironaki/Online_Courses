
#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file

;; put your code below


;; 1
(define (sequence low high stride)
  (if (> low high)
      null
      (cons low (sequence (+ low stride) high stride))))

;; 2
(define (string-append-map xs suffix)
  (map (lambda (x) (string-append x suffix)) xs))

;; 3
(define (list-nth-mod xs n)
  (cond [(< n 0) (error "list-nth-mod: negative number")]
        [(null? xs) (error "list-nth-mod: empty list")]
        [#t (car (list-tail xs (remainder n (length xs))))]))

;; 4 This one is considered poor style, since it evaluates s twice
;(define (stream-for-n-steps s n)
;  (if (= n 0)
;    null
;     (cons (car (s)) (stream-for-n-steps (cdr(s)) (- n 1)))))

;; 4
(define (stream-for-n-steps s n)
  (if (= n 0)
      null
      (let ([eval (s)])
        (cons (car eval) (stream-for-n-steps (cdr eval) (- n 1))))))

;; 5
(define funny-number-stream
  (letrec ([f (lambda (x) (cons
                           (if (= (remainder x 5) 0) (- x) x)
                           (lambda () (f (+ x 1)))))])
    (lambda () (f 1))))

;; 6
(define dan-then-dog
  (letrec ([dan (lambda () (cons "dan.jpg" dog))]
           [dog (lambda () (cons "dog.jpg" dan))])
    dan))

;; 7
(define (stream-add-zero s)
  (lambda ()
    (let ([eval (s)])
      (cons (cons 0 (car eval)) (stream-add-zero (cdr eval))))))

;; 8
(define (cycle-lists xs ys)
  (letrec ([f (lambda (x) (cons
                           (cons (list-nth-mod xs x) (list-nth-mod ys x))
                           (lambda () (f (+ x 1)))))])
    (lambda () (f 0))))

;; 9
(define (vector-assoc v vec)
  (letrec ([length (vector-length vec)]
           [f (lambda (n)
                (if (= n length)
                    #f
                    (let ([item (vector-ref vec n)])
                      (cond [(cons? item)
                             (if (equal? v (car item))
                                 item
                                 (f (+ 1 n)))]
                            [#t (f (+ 1 n))]))))])
    (f 0)))
                            
;; 10
(define (cached-assoc xs n)
  (let ([cache (make-vector n #f)]
        [pos 0])
    (lambda (v) (let ([found (vector-assoc v cache)])
                  (if found
                      found
                      (let ([found-in-xs (assoc v xs)])
                        (if found-in-xs
                            (begin (vector-set! cache pos found-in-xs)
                                   (if (= pos (- n 1))
                                       (set! pos 0)
                                       (set! pos (+ pos 1)))
                                   found-in-xs)
                            #f)))))))

;; 11



















