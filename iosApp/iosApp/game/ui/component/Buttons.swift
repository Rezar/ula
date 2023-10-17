//
//  Buttons.swift
//  iosApp
//
//  Created by Hao Wu on 9/18/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct NextButton: View {
    var onClick: () -> Void
    var width: CGFloat = 100.0
    var height: CGFloat = 60.0
    var enableButton: Bool = true
    
    var body: some View {
        Button {
            onClick()
        } label: {
            Image("next")
                .resizable()
                .frame(width: width, height: height)
                .disabled(!enableButton)
                .opacity(enableButton ? 1.0 : 0.3)
        }
    }
}

struct BackButton: View {
    var onClick: () -> Void
    var width: CGFloat = 100.0
    var height: CGFloat = 60.0
    var enableButton: Bool = true
    
    var body: some View {
        Button {
            onClick()
        } label: {
            Image("back")
                .resizable()
                .frame(width: width, height: height)
                .disabled(!enableButton)
                .opacity(enableButton ? 1.0 : 0.3)
        }
    }
}

struct CheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        return HStack {
            Image(systemName: configuration.isOn ? "checkmark.square" : "square")
                .resizable()
                .frame(width: 22, height: 22)
                .onTapGesture { configuration.isOn.toggle() }
            configuration.label

        }
    }
}
