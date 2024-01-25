//
//  DateTimeUtil.swift
//  iosApp
//
//  Created by Hao Wu on 9/17/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

class DateTimeUtil {
    /*
    * Get the current date and time
    * default as UTC
    * */
    static func getCurrentDateTime() -> Date {
        return Date()
    }
    
    /*
    * Get the time difference
    * */
    static func getDayDifference(next: Date, previous: Date) -> Int {
        return Calendar.current.dateComponents([.day], from: previous, to: next).day ?? 0
    }
}
