//
//  SettingAdvancedScreen.swift
//  iosApp
//
//  Created by Hao Wu on 9/7/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct SettingAdvancedScreen: View {
    
    @ObservedObject var settingState: SettingState
    
    init() {
        self.settingState = SettingState()
    }
    
    // TODO: UI needs fix
    var body: some View {
        
        VStack(alignment: .leading, spacing: 20.0) {
            
            // Max Threshold (Percentage)
            HStack{
                Text("Max Threshold (Percentage)")
                Spacer()
                TextField("Enter your name", value: $settingState.maxThreshold, formatter: NumberFormatter())
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 50)
            }
            
            // Min Threshold (Percentage)
            HStack{
                Text("Min Threshold (Percentage)")
                Spacer()
                TextField("Enter your name", value: $settingState.minThreshold, formatter: NumberFormatter())
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 50)
            }
            
            Spacer()
            
            // Button to save all advanced setting changes
            Button("Save!") {
                settingState.writeMaxThreshold(maxThreshold: settingState.maxThreshold)
                settingState.writeMinThreshold(minThreshold: settingState.minThreshold)
            }
            .padding(12)
            .foregroundStyle(.white)
            .background(
                RoundedRectangle(cornerRadius: 10)
                    .fill(Color.gray)
//                        .shadow(color: .gray, radius: 2, x: 0, y: 2)
            )
            .clipShape(Rectangle())
        }
        .padding(.horizontal, 20)
        
    }
    
}

struct SettingAdvancedScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingAdvancedScreen()
    }
}
