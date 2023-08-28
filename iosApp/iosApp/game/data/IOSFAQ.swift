//
//  IOSFAQ.swift
//  iosApp
//
//  Created by Hao Wu on 8/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct IOSFAQ: Identifiable, Hashable {
    var id: Int
    let question: String
    let answer: String
    
    static func samples() -> [IOSFAQ] {
        
//        (0..<7).map(IOSFAQ.fixture)
        
        // TODO: delete answer field after building the answer views
        return [IOSFAQ(id: 1, question: "1. How to use our app?", answer: "1"),
        IOSFAQ(id: 2, question: "2. How to read the graphs?", answer: "2"),
        IOSFAQ(id: 3, question: "3. How to reach your fitness goal to make the monster happy?", answer: "3"),
        IOSFAQ(id: 4, question: "4. Want to take a day off?", answer: "4"),
        IOSFAQ(id: 5, question: "5. How to use our app?", answer: "5"),
        IOSFAQ(id: 6, question: "6. How to use our app?", answer: "6")]
        
        
    }
    
//    private static func fixture(_ id: Int) -> IOSFAQ {
//
//        IOSFAQ(
//            id: id,
//            question: "Q #\(id)",
//            answer: "Ans #\(id)"
//        )
//        IOSFAQ(question: "1. How to use our app?", answers: "1")
//        IOSFAQ(question: "2. How to read the graphs?", answers: "2")
//        IOSFAQ(question: "3. How to reach your fitness goal to make the monster happy?", answers: "3")
//        IOSFAQ(question: "4. Want to take a day off?", answers: "4")
//        IOSFAQ(question: "5. How to use our app?", answers: "5")
//        IOSFAQ(question: "6. How to use our app?", answers: "6")
        
//    }
    
}

