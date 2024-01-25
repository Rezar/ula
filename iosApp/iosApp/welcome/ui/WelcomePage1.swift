//
//  WelcomePage1.swift
//  iosApp
//
//  Created by Hao Wu on 9/18/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct WelcomePage1: View {
    var onNextButtonClicked: () -> Void = {}
    
    
    @State private var checkedState = false
    
    var body: some View {
        VStack {
            VStack {
                HStack {
                    Text("WELCOME!")
                        .font(.WelcomeTitle)
                        .foregroundColor(.red)
                }
                
                Spacer()
                
                VStack {
                    Spacer()
                    Text("A magic has happened and now you own an egg of a monster, ULA!")
                        .font(.WelcomeBody)
                    Spacer()
                    Text("As much as you stay committed to your daily walk/run routine your ULA is happy, being lazy makes the ULA happy.")
                        .font(.WelcomeBody)
                }
            
            }
            .padding(.leading, 20)
            .padding(.trailing, 20)
            .padding(.top, 50)
            .padding(.bottom, 0)
            
            Spacer()
            
            VStack {
                Spacer()
                HStack(alignment: .center) {
                    Toggle(isOn: $checkedState) {
                        Text("I agree with Terms of Service / Privacy Policy")
                            .font(.WelcomeCaption)
                    }
                    .toggleStyle(CheckboxToggleStyle())
                }
                
                HStack(alignment: .center) {
                    Spacer()
                    NextButton(
                        onClick: {
                            onNextButtonClicked()
                        },
                        enableButton: checkedState
                    )
                }
                .padding(.horizontal, 10)
            }
        }
    }
}



struct WelcomePage1_Previews: PreviewProvider {
    static var previews: some View {
        WelcomePage1(
            onNextButtonClicked: {}
        )
    }
}

