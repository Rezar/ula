//
//  IOSSettingViewModel2.swift
//  iosApp
//
//  Created by Hao Wu on 9/18/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

let SavedDisplaySteps = "SavedDisplaySteps"
let SavedDisplayMonster = "SavedDisplayMonster"
let SavedMaxThreshold = "SavedMaxThreshold"
let SavedMinThreshold = "SavedMinThreshold"
let SavedEffectiveDate = "SavedEffectiveDate"
let SavedEffectiveDays = "SavedEffectiveDays"
let SavedDailyGoal = "SavedDailyGoal"
let SavedWeeklyGoal = "SavedWeeklyGoal"

// TODO: "SavedGoal" is not available, I can't change the its value.
class SettingState: ObservableObject {
    
    @Published var stepCountSwitchON = UserDefaults.standard.object(forKey: SavedDisplaySteps) as? Bool ?? false
    @Published var progressMonsterSwitchON = UserDefaults.standard.object(forKey: SavedDisplayMonster) as? Bool ?? false
    @Published var maxThreshold = UserDefaults.standard.object(forKey: SavedMaxThreshold) as? Double ?? 0.2
    @Published var minThreshold = UserDefaults.standard.object(forKey: SavedMinThreshold) as? Double ?? 0.2
    @Published var effectiveDate = UserDefaults.standard.object(forKey: SavedEffectiveDate) as? Date ?? Date()
    @Published var effectiveDays = UserDefaults.standard.object(forKey: SavedEffectiveDays) as? Int ?? 3
    @Published var tabIndex = 0
    @Published var dailyGoal = UserDefaults.standard.object(forKey: SavedDailyGoal) as? Double ?? 5000.0
    @Published var weeklyGoal = UserDefaults.standard.object(forKey: SavedWeeklyGoal) as? Double ?? 20000.0
    
    
    func writeDisplaySteps(displaySteps: Bool) {
        UserDefaults.standard.set(displaySteps, forKey: SavedDisplaySteps)
    }
    
    func writeDisplayMonster(displayMonster: Bool) {
        UserDefaults.standard.set(displayMonster, forKey: SavedDisplayMonster)
    }
    
    func writeMaxThreshold(maxThreshold: Double) {
        UserDefaults.standard.set(maxThreshold, forKey: SavedMaxThreshold)
    }
    
    func writeMinThreshold(minThreshold: Double) {
        UserDefaults.standard.set(minThreshold, forKey: SavedMinThreshold)
    }
    
    func writeEffectiveDays(effectiveDays: Int) {
        UserDefaults.standard.set(effectiveDays, forKey: SavedEffectiveDays)
    }
    
    func writeEffectiveDate(effectiveDate: Date) {
        UserDefaults.standard.set(effectiveDate, forKey: SavedEffectiveDate)
    }
    
    func writeDailyGoal(dailyGoal: Int) {
        UserDefaults.standard.set(dailyGoal, forKey: SavedDailyGoal)
    }
    
    func writeWeeklyGoal(weeklyGoal: Int) {
        UserDefaults.standard.set(weeklyGoal, forKey: SavedWeeklyGoal)
    }
}
