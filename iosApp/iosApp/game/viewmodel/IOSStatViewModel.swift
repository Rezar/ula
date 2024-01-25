//
//  IOSStatViewModel.swift
//  iosApp
//
//  Created by Hao Wu on 9/6/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class IOSStatViewModel: ObservableObject {
    
    let defaults = UserDefaults.standard
    
    // fake data and save it to the userDefaults DB
    let stepHistory: [StepHistory] = [
        .init(date: Date.from(year: 2023, month: 09, day: 07), stepCount: 3000),
        .init(date: Date.from(year: 2023, month: 09, day: 08), stepCount: 4890),
        .init(date: Date.from(year: 2023, month: 09, day: 09), stepCount: 9987),
        .init(date: Date.from(year: 2023, month: 09, day: 10), stepCount: 15000),
        .init(date: Date.from(year: 2023, month: 09, day: 11), stepCount: 24000),
        .init(date: Date.from(year: 2023, month: 09, day: 12), stepCount: 5000)
    ]
    let goal = 10000 // TODO: ------------------------------
    
    
    // Read
    func readStepHistory() -> [StepHistory] {
        
        defaults.save(customObject: stepHistory, forKey: "SavedStepHistory")
        defaults.set(goal, forKey: "SavedGoal") // TODO: ------------------------------
//        print(defaults.retrieve(customObjectForKey: "SavedStepHistory"))
        
        let readStepHistory = defaults.retrieve(customObjectForKey: "SavedStepHistory") as? [StepHistory] ?? [StepHistory]()
        
        return readStepHistory
    }
    
    
    // Write
    // TODO: Should add the step count from the sensor and the periodic task management of current date
    // TODO: Should write some if statements to remove the first record of the stepHistory if the number of records is the max of allowed
    // param: step is the step count of today from sensor and periodic task management
    func addStepRecord(step: Int) {
        
        var temp = readStepHistory()
        temp.append(StepHistory(date: Date(), stepCount: step))
        
        defaults.save(customObject: temp, forKey: "SavedStepHistory")
        
    }
    
}


struct StepHistory: Codable, Identifiable {
    
    let id = UUID()
    let date: Date
    let stepCount: Int
    
}

extension Date {
    static func from(year: Int, month: Int, day: Int) -> Date {
        let components = DateComponents(year: year, month: month, day: day)
        return Calendar.current.date(from: components)!
    }
}

extension UserDefaults {
    func save(customObject object: [StepHistory], forKey key: String) {
        let encoder = JSONEncoder()
        if let encodedObject = try? encoder.encode(object) {
            set(encodedObject, forKey: key)
        }
    }
    
    func retrieve(customObjectForKey key: String) -> [StepHistory]? {
        guard let data = data(forKey: key) else { return nil }
        let decoder = JSONDecoder()
        return try? decoder.decode([StepHistory].self, from: data)
    }
}

