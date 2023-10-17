//
//  Config.swift
//  iosApp
//
//  Created by Hao Wu on 9/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Default {
    struct board {
        static let size: CGFloat = 300
        static let padding: CGFloat = 10
        static let backgroundColor: Color = .white
    }
    struct cell {
        static let size: CGFloat = 60
        static let padding: CGFloat = 60
        static let backgroundColor: Color = .white
    }
    struct boardLine {
        static let thickness: CGFloat = 5
        static let color: Color = .red
    }
    struct label {
        struct circle {
            static let size: CGFloat = 60
            static let padding: CGFloat = 5
            static let color: Color = .green
            static let thickness: CGFloat = 20
        }
        struct cross {
            static let size: CGFloat = 60
            static let padding: CGFloat = 5
            static let color: Color = .yellow
            static let thickness: CGFloat = 20
        }
    }
}
