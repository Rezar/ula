//
//  HealthStore.swift
//  iosApp
//
//  Created by Hao Wu on 12/3/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import HealthKit

extension Date {
    static var startOfDay: Date? {
        Calendar.current.date(byAdding: .day, value: -7, to: Date())
    }
}

class HealthManager: ObservableObject {
    let healthStore = HKHealthStore()
    
    func requestAuthorization(completion: @escaping (Bool, Error?) -> Void) {
        guard HKHealthStore.isHealthDataAvailable() else {
            completion(false, nil)
            return
        }
        
        let typesToRoad: Set<HKObjectType> = [HKObjectType.quantityType(forIdentifier: .stepCount)!]
        
        healthStore.requestAuthorization(toShare: nil, read: typesToRoad) { success, error in
            DispatchQueue.main.async {
                completion(success, error)
            }
        }
    }
    
    
    
    func fetchTodaySteps(completion: @escaping ([StepHistory], Error?) -> Void) {
        let calendar = Calendar.current
        let now = Date()
        let startDate = calendar.startOfDay(for: calendar.date(byAdding: .day, value: -6, to: now)!)
        
        print(startDate, now)
        
        guard let stepCountType = HKObjectType.quantityType(forIdentifier: .stepCount) else {
            completion([], nil)
            return
        }
        
        let predicate = HKQuery.predicateForSamples(withStart: startDate, end: now, options: .strictStartDate)
        
        let query = HKStatisticsCollectionQuery(
            quantityType: stepCountType,
            quantitySamplePredicate: predicate,
            options: .cumulativeSum,
            anchorDate: startDate,
            intervalComponents: DateComponents(day: 1)
        )
        
        query.initialResultsHandler = { query, result, error in
            guard let result = result else {
                completion([], error)
                return
            }
            var stepHistory: [StepHistory] = []
            result.enumerateStatistics(from: startDate, to: now) { statistics, stop in
                if let sum = statistics.sumQuantity() {
                    let date = statistics.startDate
                    let steps = Int(sum.doubleValue(for: .count()))
                    stepHistory.append(StepHistory(date: date, stepCount: steps))
                }
            }
            
            print("HealthStore \(stepHistory)")
            
            completion(stepHistory, nil)
        }
        
        healthStore.execute(query)
    }
}
