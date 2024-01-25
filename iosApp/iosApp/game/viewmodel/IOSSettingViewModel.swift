//
//  IOSSettingViewModel.swift
//  iosApp
//
//  Created by Hao Wu on 9/7/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class IOSSettingViewModel: ObservableObject {
    
    let defaults = UserDefaults.standard
    
    func readDisplaySteps() -> Bool {
        return defaults.object(forKey: "SavedDisplaySteps") as? Bool ?? false
    }
    func writeDisplaySteps(displaySteps: Bool) {
        defaults.set(displaySteps, forKey: "SavedDisplaySteps")
    }
    
    func readDisplayMonster() -> Bool {
        return defaults.object(forKey: "SavedDisplayMonster") as? Bool ?? false
    }
    func writeDisplayMonster(displayMonster: Bool) {
        defaults.set(displayMonster, forKey: "SavedDisplayMonster")
    }
    
    func readMaxThreshold() -> Double {
        return defaults.object(forKey: "SavedMaxThreshold") as? Double ?? 0.2
    }
    func writeMaxThreshold(maxThreshold: Double) {
        defaults.set(maxThreshold, forKey: "SavedMaxThreshold")
    }
    
    func readMinThreshold() -> Double {
        return defaults.object(forKey: "SavedMinThreshold") as? Double ?? 0.2
    }
    func writeMinThreshold(minThreshold: Double) {
        defaults.set(minThreshold, forKey: "SavedMinThreshold")
    }
    
    func readEffectiveDays() -> Int {
        return defaults.object(forKey: "SavedEffectiveDays") as? Int ?? 3
    }
    func writeEffectiveDays(effectiveDays: Int) {
        defaults.set(effectiveDays, forKey: "SavedEffectiveDays")
    }
    
    func readEffectiveDate() -> Date {
        return defaults.object(forKey: "SavedEffectiveDate") as? Date ?? Date()
    }
    func writeEffectiveDate(effectiveDate: Date) {
        defaults.set(effectiveDate, forKey: "SavedEffectiveDate")
    }
    
    func readGoal() -> Int {
        return defaults.object(forKey: "SavedGoal") as? Int ?? 5000
    }
    func writeGoal(goal: Int) {
        defaults.set(goal, forKey: "SavedGoal")
    }
    
}
